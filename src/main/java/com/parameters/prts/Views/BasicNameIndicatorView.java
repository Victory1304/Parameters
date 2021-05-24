package com.parameters.prts.Views;

import com.parameters.prts.Model.BaseEntity;
import com.parameters.prts.Model.BasicNameIndicatorEntity;
import com.parameters.prts.Model.GroupSystemEntity;
import com.parameters.prts.Model.SystemEntity;
import com.parameters.prts.Model.TypeEntity;
import com.parameters.prts.Service.BasicNameIndicatorService;
import com.parameters.prts.Service.GroupSystemService;
import com.parameters.prts.Service.SystemService;
import com.parameters.prts.Service.TypeService;
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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.artur.helpers.CrudServiceDataProvider;

import java.util.Optional;

@Route(value = "basicNameIndicator", layout = MainView.class)
@PageTitle("Индикаторы")
@CssImport("./views/basicNameIndicator/basicNameIndicator-view.css")
public class BasicNameIndicatorView extends CommonEntityLayout {
    private final static String ITEM_SAVED = "Индикатор сохранён";
    private final static String ITEM_SAVE_FAIL = "Не удалось сохранить";
    private final static String ITEM_DELETION = "Удаление объекта";
    private final static String ITEM_DELETION_CONFIRMATION = "Вы действительно хотите удалить объект ";
    private final static String ITEM_DELETED = "Индикатор удалён";

    private final BasicNameIndicatorService basicNameIndicatorService;
    private final TypeService typeService;
    private final SystemService systemService;
    private final GroupSystemService groupSystemService;
    private final Grid<BasicNameIndicatorEntity> grid = new Grid<>(BasicNameIndicatorEntity.class, false);

    private TextField latinName;
    private TextField unitMeasure;
    private TextField description;
    private ComboBox<TypeEntity> type;
    private ComboBox<SystemEntity> system;
    private ComboBox<GroupSystemEntity> groupSystem;

    private BasicNameIndicatorEntity basicNameIndicatorEntity;

    public BasicNameIndicatorView(BasicNameIndicatorService basicNameIndicatorService, TypeService typeService,
                                  SystemService systemService, GroupSystemService groupSystemService) {
        this.basicNameIndicatorService = basicNameIndicatorService;
        this.typeService = typeService;
        this.systemService = systemService;
        this.groupSystemService = groupSystemService;
        addClassName("basicNameIndicator-view");

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        createGridLayout(splitLayout, grid);
        createEditorLayout(splitLayout);
        add(splitLayout);

        grid.addColumn(BasicNameIndicatorEntity::getLatinName).setHeader("Латинское название").setAutoWidth(true);
        grid.addColumn(BasicNameIndicatorEntity::getUnitMeasure).setHeader("Единица измереия").setAutoWidth(true);
        grid.addColumn(BasicNameIndicatorEntity::getDescription).setHeader("Описание").setAutoWidth(true);
        grid.addColumn(BasicNameIndicatorEntity::getType).setHeader("Тип").setAutoWidth(true);
        grid.addColumn(BasicNameIndicatorEntity::getSystem).setHeader("Система").setAutoWidth(true);
        grid.addColumn(BasicNameIndicatorEntity::getGroupSystem).setHeader("Группа").setAutoWidth(true);

        grid.setDataProvider(new CrudServiceDataProvider<>(this.basicNameIndicatorService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<BasicNameIndicatorEntity> basicNameIndicatorFromBackend
                        = this.basicNameIndicatorService.get(event.getValue().getId());
                if (basicNameIndicatorFromBackend.isPresent()) {
                    populateForm(basicNameIndicatorFromBackend.get());
                } else {
                    refreshGrid(grid);
                }
            } else {
                clearForm();
            }
        });

        setBinder(new BeanValidationBinder<>(BasicNameIndicatorEntity.class));
        getBinder().bindInstanceFields(this);

        getCancel().addClickListener(event -> {
            clearForm();
            refreshGrid(grid);
        });

        getSave().addClickListener(event -> {
            try {
                if (this.basicNameIndicatorEntity == null) {
                    this.basicNameIndicatorEntity = new BasicNameIndicatorEntity();
                }
                getBinder().writeBean(this.basicNameIndicatorEntity);

                this.basicNameIndicatorService.update(this.basicNameIndicatorEntity);
                clearForm();
                refreshGrid(grid);
                Notification.show(ITEM_SAVED);
            } catch (ValidationException validationException) {
                Notification.show(ITEM_SAVE_FAIL);
            }
        });

        getDelete().addClickListener(event -> {
            if (this.basicNameIndicatorEntity != null && this.basicNameIndicatorEntity.getId() != null) {
                ConfirmationDialog confirmationDialog = new ConfirmationDialog(ITEM_DELETION,
                        ITEM_DELETION_CONFIRMATION + this.basicNameIndicatorEntity.getId() + " ?", confirm -> {
                    this.basicNameIndicatorService.delete(this.basicNameIndicatorEntity.getId());
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

        latinName = new TextField("Латинское название");
        unitMeasure = new TextField("Единица измерения");
        description = new TextField("Описание");
        type = new ComboBox<>("Тип");
        type.setDataProvider(new CrudServiceDataProvider<>(this.typeService));
        system = new ComboBox<>("Система");
        system.setDataProvider(new CrudServiceDataProvider<>(this.systemService));
        groupSystem = new ComboBox<>("Группа");
        groupSystem.setDataProvider(new CrudServiceDataProvider<>(this.groupSystemService));
        Component[] fields = new Component[]{
                latinName, unitMeasure, description, type, system, groupSystem
        };
        FormLayout formLayout = new FormLayout();

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
        this.basicNameIndicatorEntity = (BasicNameIndicatorEntity) entity;
        getBinder().readBean(this.basicNameIndicatorEntity);
    }

    @Override
    public BeanValidationBinder<BasicNameIndicatorEntity> getBinder() {
        return ((BeanValidationBinder<BasicNameIndicatorEntity>) super.getBinder());
    }
}
