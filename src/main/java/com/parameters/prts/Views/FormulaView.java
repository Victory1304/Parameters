package com.parameters.prts.Views;

import com.parameters.prts.Model.BaseEntity;
import com.parameters.prts.Model.FormulaEntity;
import com.parameters.prts.Service.FormulaService;
import com.parameters.prts.Views.components.CommonEntityLayout;
import com.parameters.prts.Views.components.ConfirmationDialog;
import com.parameters.prts.Views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.artur.helpers.CrudServiceDataProvider;

import java.util.Optional;

@Route(value = "formula", layout = MainView.class)
@PageTitle("Оборудование")
@CssImport("./views/formula/formula-view.css")
public class FormulaView extends CommonEntityLayout {
    private final static String ITEM_SAVED = "Формула сохранена";
    private final static String ITEM_SAVE_FAIL = "Не удалось сохранить";
    private final static String ITEM_DELETION = "Удаление объекта";
    private final static String ITEM_DELETION_CONFIRMATION = "Вы действительно хотите удалить объект ";
    private final static String ITEM_DELETED = "Формула удалена";

    private final FormulaService formulaService;
    private final Grid<FormulaEntity> grid = new Grid<>(FormulaEntity.class, false);

    private TextField calculation;
    private TextField normalValue;
    private TextField borderLineValue;

    private FormulaEntity formulaEntity;

    public FormulaView(FormulaService formulaService) {
        this.formulaService = formulaService;
        addClassName("formula-view");

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        createGridLayout(splitLayout, grid);
        createEditorLayout(splitLayout);
        add(splitLayout);

        grid.addColumn(FormulaEntity::getCalculation).setHeader("Уравнение").setAutoWidth(true);
        grid.addColumn(FormulaEntity::getNormalValue).setHeader("Нормальное значение").setAutoWidth(true);
        grid.addColumn(FormulaEntity::getBorderlineValue).setHeader("Граничное значение").setAutoWidth(true);

        grid.setDataProvider(new CrudServiceDataProvider<>(this.formulaService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<FormulaEntity> formulaFromBackend = this.formulaService.get(event.getValue().getId());
                if (formulaFromBackend.isPresent()) {
                    populateForm(formulaFromBackend.get());
                } else {
                    refreshGrid(grid);
                }
            } else {
                clearForm();
            }
        });

        setBinder(new BeanValidationBinder<>(FormulaEntity.class));
        getBinder().forField(normalValue).withConverter(new StringToDoubleConverter("Число"))
                   .bind(FormulaEntity::getNormalValue, FormulaEntity::setNormalValue);
        getBinder().bindInstanceFields(this);

        getCancel().addClickListener(event -> {
            clearForm();
            refreshGrid(grid);
        });

        getSave().addClickListener(event -> {
            try {
                if (this.formulaEntity == null) {
                    this.formulaEntity = new FormulaEntity();
                }
                getBinder().writeBean(this.formulaEntity);

                this.formulaService.update(this.formulaEntity);
                clearForm();
                refreshGrid(grid);
                Notification.show(ITEM_SAVED);
            } catch (ValidationException validationException) {
                Notification.show(ITEM_SAVE_FAIL);
            }
        });

        getDelete().addClickListener(event -> {
            if (this.formulaEntity != null && this.formulaEntity.getId() != null) {
                ConfirmationDialog confirmationDialog = new ConfirmationDialog(ITEM_DELETION,
                        ITEM_DELETION_CONFIRMATION + this.formulaEntity.getNormalValue() + " ?", confirm -> {
                    this.formulaService.delete(this.formulaEntity.getId());
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
        calculation = new TextField("Формула");
        normalValue = new TextField("Нормальное значение");
        borderLineValue = new TextField("Граничное значение");
        Component[] fields = new Component[]{calculation, normalValue, borderLineValue};

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
        this.formulaEntity = (FormulaEntity) entity;
        getBinder().readBean(this.formulaEntity);
    }

    @Override
    public BeanValidationBinder<FormulaEntity> getBinder() {
        return ((BeanValidationBinder<FormulaEntity>) super.getBinder());
    }
}
