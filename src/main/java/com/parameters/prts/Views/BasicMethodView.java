package com.parameters.prts.Views;

import com.parameters.prts.Model.BaseEntity;
import com.parameters.prts.Model.BasicMethodEntity;
import com.parameters.prts.Model.BasicNameIndicatorEntity;
import com.parameters.prts.Model.MethodEntity;
import com.parameters.prts.Service.BasicMethodService;
import com.parameters.prts.Service.BasicNameIndicatorService;
import com.parameters.prts.Service.MethodService;
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

@Route(value = "basicMethod", layout = MainView.class)
@PageTitle("Индикаторы методов")
@CssImport("./views/basicMethod/basicMethod-view.css")
public class BasicMethodView extends CommonEntityLayout {
    private final static String ITEM_SAVED = "Связь сохранена";
    private final static String ITEM_SAVE_FAIL = "Не удалось сохранить";
    private final static String ITEM_DELETION = "Удаление связи";
    private final static String ITEM_DELETION_CONFIRMATION = "Вы действительно хотите удалить связь ";
    private final static String ITEM_DELETED = "Связь удалена";

    private final BasicMethodService basicMethodService;
    private final BasicNameIndicatorService basicNameIndicatorService;
    private final MethodService methodService;
    private final Grid<BasicMethodEntity> grid = new Grid<>(BasicMethodEntity.class, false);

    private ComboBox<MethodEntity> method;
    private ComboBox<BasicNameIndicatorEntity> basicNameIndicator;

    private BasicMethodEntity basicMethodEntity;

    public BasicMethodView(BasicMethodService basicMethodService,
                            BasicNameIndicatorService basicNameIndicatorService, MethodService methodService) {
        this.basicMethodService = basicMethodService;
        this.basicNameIndicatorService = basicNameIndicatorService;
        this.methodService = methodService;
        addClassName("basicMethod-view");

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        createGridLayout(splitLayout, grid);
        createEditorLayout(splitLayout);
        add(splitLayout);

        grid.addColumn(BasicMethodEntity::getBasicNameIndicator).setHeader("Название индикатора").setAutoWidth(true);
        grid.addColumn(BasicMethodEntity::getMethod).setHeader("Метод").setAutoWidth(true);

        grid.setDataProvider(new CrudServiceDataProvider<>(this.basicMethodService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<BasicMethodEntity> basicMethodFromBackend = this.basicMethodService.get(event.getValue().getId());
                if (basicMethodFromBackend.isPresent()) {
                    populateForm(basicMethodFromBackend.get());
                } else {
                    refreshGrid(grid);
                }
            } else {
                clearForm();
            }
        });

        setBinder(new BeanValidationBinder<>(BasicMethodEntity.class));
        getBinder().bindInstanceFields(this);

        getCancel().addClickListener(event -> {
            clearForm();
            refreshGrid(grid);
        });

        getSave().addClickListener(event -> {
            try {
                if (this.basicMethodEntity == null) {
                    this.basicMethodEntity = new BasicMethodEntity();
                }
                getBinder().writeBean(this.basicMethodEntity);

                this.basicMethodService.update(this.basicMethodEntity);
                clearForm();
                refreshGrid(grid);
                Notification.show(ITEM_SAVED);
            } catch (ValidationException validationException) {
                Notification.show(ITEM_SAVE_FAIL);
            }
        });

        getDelete().addClickListener(event -> {
            if (this.basicMethodEntity != null && this.basicMethodEntity.getId() != null) {
                ConfirmationDialog confirmationDialog = new ConfirmationDialog(ITEM_DELETION,
                        ITEM_DELETION_CONFIRMATION + this.basicMethodEntity.getId() + " ?", confirm -> {
                    this.basicMethodService.delete(this.basicMethodEntity.getId());
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
        method = new ComboBox<>("Метод");
        method.setDataProvider(new CrudServiceDataProvider<>(this.methodService));
        Component[] fields = new Component[]{basicNameIndicator, method};

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
        this.basicMethodEntity = (BasicMethodEntity) entity;
        getBinder().readBean(this.basicMethodEntity);
    }

    @Override
    public BeanValidationBinder<BasicMethodEntity> getBinder() {
        return ((BeanValidationBinder<BasicMethodEntity>) super.getBinder());
    }
}
