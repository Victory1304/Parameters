package com.parameters.prts.Views;

import com.parameters.prts.Model.BaseEntity;
import com.parameters.prts.Model.EquipmentEntity;
import com.parameters.prts.Service.EquipmentService;
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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.artur.helpers.CrudServiceDataProvider;

import java.util.Optional;

@Route(value = "equipment", layout = MainView.class)
@PageTitle("Оборудование")
@CssImport("./views/equipment/equipment-view.css")
public class EquipmentView extends CommonEntityLayout {
    private final static String ITEM_SAVED = "Оборудование сохранено";
    private final static String ITEM_SAVE_FAIL = "Не удалось сохранить";
    private final static String ITEM_DELETION = "Удаление объекта";
    private final static String ITEM_DELETION_CONFIRMATION = "Вы действительно хотите удалить объект ";
    private final static String ITEM_DELETED = "Оборудование удалено";

    private final EquipmentService equipmentService;
    private final Grid<EquipmentEntity> grid = new Grid<>(EquipmentEntity.class, false);

    private TextField name;

    private EquipmentEntity equipmentEntity;

    public EquipmentView(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
        addClassName("equipment-view");

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        createGridLayout(splitLayout, grid);
        createEditorLayout(splitLayout);
        add(splitLayout);

        grid.addColumn(EquipmentEntity::getName).setHeader("Название").setAutoWidth(true);

        grid.setDataProvider(new CrudServiceDataProvider<>(this.equipmentService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<EquipmentEntity> equipmentFromBackend = this.equipmentService.get(event.getValue().getId());
                if (equipmentFromBackend.isPresent()) {
                    populateForm(equipmentFromBackend.get());
                } else {
                    refreshGrid(grid);
                }
            } else {
                clearForm();
            }
        });

        setBinder(new BeanValidationBinder<>(EquipmentEntity.class));
        getBinder().bindInstanceFields(this);

        getCancel().addClickListener(event -> {
            clearForm();
            refreshGrid(grid);
        });

        getSave().addClickListener(event -> {
            try {
                if (this.equipmentEntity == null) {
                    this.equipmentEntity = new EquipmentEntity();
                }
                getBinder().writeBean(this.equipmentEntity);

                this.equipmentService.update(this.equipmentEntity);
                clearForm();
                refreshGrid(grid);
                Notification.show(ITEM_SAVED);
            } catch (ValidationException validationException) {
                Notification.show(ITEM_SAVE_FAIL);
            }
        });

        getDelete().addClickListener(event -> {
            if (this.equipmentEntity != null && this.equipmentEntity.getId() != null) {
                ConfirmationDialog confirmationDialog = new ConfirmationDialog(ITEM_DELETION,
                        ITEM_DELETION_CONFIRMATION + this.equipmentEntity.getName() + " ?", confirm -> {
                    this.equipmentService.delete(this.equipmentEntity.getId());
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
        name = new TextField("Название");
        Component[] fields = new Component[]{name};

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
        this.equipmentEntity = (EquipmentEntity) entity;
        getBinder().readBean(this.equipmentEntity);
    }

    @Override
    public BeanValidationBinder<EquipmentEntity> getBinder() {
        return ((BeanValidationBinder<EquipmentEntity>) super.getBinder());
    }
}
