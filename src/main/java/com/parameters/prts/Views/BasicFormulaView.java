package com.parameters.prts.Views;

import com.parameters.prts.Model.BaseEntity;
import com.parameters.prts.Model.BasicFormulaEntity;
import com.parameters.prts.Model.BasicNameIndicatorEntity;
import com.parameters.prts.Model.FormulaEntity;
import com.parameters.prts.Service.BasicFormulaService;
import com.parameters.prts.Service.BasicNameIndicatorService;
import com.parameters.prts.Service.FormulaService;
import com.parameters.prts.Views.components.CommonEntityLayout;
import com.parameters.prts.Views.components.ConfirmationDialog;
import com.parameters.prts.Views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.artur.helpers.CrudServiceDataProvider;

import java.util.Optional;

@Route(value = "basicFormula", layout = MainView.class)
@PageTitle("Индикаторы формул")
@CssImport("./views/basicFormula/basicFormula-view.css")
public class BasicFormulaView extends CommonEntityLayout {
    private final static String ITEM_SAVED = "Связь сохранена";
    private final static String ITEM_SAVE_FAIL = "Не удалось сохранить";
    private final static String ITEM_DELETION = "Удаление связи";
    private final static String ITEM_DELETION_CONFIRMATION = "Вы действительно хотите удалить связь ";
    private final static String ITEM_DELETED = "Связь удалена";

    private final BasicFormulaService basicFormulaService;
    private final BasicNameIndicatorService basicNameIndicatorService;
    private final FormulaService formulaService;
    private final Grid<BasicFormulaEntity> grid = new Grid<>(BasicFormulaEntity.class, false);

    private ComboBox<FormulaEntity> formula;
    private ComboBox<BasicNameIndicatorEntity> basicNameIndicator;

    private BasicFormulaEntity basicFormulaEntity;

    public BasicFormulaView(BasicFormulaService basicFormulaService,
                            BasicNameIndicatorService basicNameIndicatorService, FormulaService formulaService) {
        this.basicFormulaService = basicFormulaService;
        this.basicNameIndicatorService = basicNameIndicatorService;
        this.formulaService = formulaService;
        addClassName("basicFormula-view");

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        createGridLayout(splitLayout, grid);
        createEditorLayout(splitLayout);
        add(splitLayout);

        grid.addColumn(BasicFormulaEntity::getBasicNameIndicator).setHeader("Название индикатора").setAutoWidth(true);
        grid.addColumn(BasicFormulaEntity::getFormula).setHeader("Формула").setAutoWidth(true);

        grid.setDataProvider(new CrudServiceDataProvider<>(this.basicFormulaService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<BasicFormulaEntity> basicFormulaFromBackend = this.basicFormulaService.get(event.getValue().getId());
                if (basicFormulaFromBackend.isPresent()) {
                    populateForm(basicFormulaFromBackend.get());
                } else {
                    refreshGrid(grid);
                }
            } else {
                clearForm();
            }
        });

        setBinder(new BeanValidationBinder<>(BasicFormulaEntity.class));
        getBinder().bindInstanceFields(this);

        getCancel().addClickListener(event -> {
            clearForm();
            refreshGrid(grid);
        });

        getSave().addClickListener(event -> {
            try {
                if (this.basicFormulaEntity == null) {
                    this.basicFormulaEntity = new BasicFormulaEntity();
                }
                getBinder().writeBean(this.basicFormulaEntity);

                this.basicFormulaService.update(this.basicFormulaEntity);
                clearForm();
                refreshGrid(grid);
                Notification.show(ITEM_SAVED);
            } catch (ValidationException validationException) {
                Notification.show(ITEM_SAVE_FAIL);
            }
        });

        getDelete().addClickListener(event -> {
            if (this.basicFormulaEntity != null && this.basicFormulaEntity.getId() != null) {
                ConfirmationDialog confirmationDialog = new ConfirmationDialog(ITEM_DELETION,
                        ITEM_DELETION_CONFIRMATION + this.basicFormulaEntity.getId() + " ?", confirm -> {
                    this.basicFormulaService.delete(this.basicFormulaEntity.getId());
                    clearForm();
                    refreshGrid(grid);
                    Notification.show(ITEM_DELETED);
                });
                confirmationDialog.open();
            }
        });
    }

    @Override
    public void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setId("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setId("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        basicNameIndicator = new ComboBox<>("Индикатор");
        basicNameIndicator.setDataProvider(new CrudServiceDataProvider<>(this.basicNameIndicatorService));
        formula = new ComboBox<>("Формула");
        formula.setDataProvider(new CrudServiceDataProvider<>(this.formulaService));
        Component[] fields = new Component[]{basicNameIndicator, formula};

        for (Component field : fields) {
            ((HasStyle) field).addClassName("full-width");
        }
        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    @Override
    public <T extends BaseEntity> void populateForm(T entity) {
        this.basicFormulaEntity = (BasicFormulaEntity) entity;
        getBinder().readBean(this.basicFormulaEntity);
    }

    @Override
    public BeanValidationBinder<BasicFormulaEntity> getBinder() {
        return ((BeanValidationBinder<BasicFormulaEntity>) super.getBinder());
    }
}
