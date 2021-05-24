package com.parameters.prts.Views;

import com.parameters.prts.Model.*;
import com.parameters.prts.Service.*;
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

@Route(value = "methodEquipment", layout = MainView.class)
@PageTitle("Оборудование методов")
@CssImport("./views/methodEquipment/methodEquipment-view.css")
public class MethodEquipmentView extends CommonEntityLayout {
    private final static String ITEM_SAVED = "Связь сохранена";
    private final static String ITEM_SAVE_FAIL = "Не удалось сохранить";
    private final static String ITEM_DELETION = "Удаление связи";
    private final static String ITEM_DELETION_CONFIRMATION = "Вы действительно хотите удалить связь ";
    private final static String ITEM_DELETED = "Связь удалена";

    private final MethodEquipmentService methodEquipmentService;
    private final MethodService methodService;
    private final EquipmentService equipmentService;
    private final Grid<MethodEquipmentEntity> grid = new Grid<>(MethodEquipmentEntity.class, false);

    private ComboBox<MethodEntity> method;
    private ComboBox<EquipmentEntity> equipment;

    private MethodEquipmentEntity methodEquipmentEntity;

    public MethodEquipmentView(MethodEquipmentService methodEquipmentService,
                            MethodService methodService, EquipmentService equipmentService) {
        this.methodEquipmentService = methodEquipmentService;
        this.methodService = methodService;
        this.equipmentService = equipmentService;
        addClassName("methodEquipment-view");

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        createGridLayout(splitLayout, grid);
        createEditorLayout(splitLayout);
        add(splitLayout);

        grid.addColumn(MethodEquipmentEntity::getMethod).setHeader("Метод").setAutoWidth(true);
        grid.addColumn(MethodEquipmentEntity::getEquipment).setHeader("Оборудование").setAutoWidth(true);

        grid.setDataProvider(new CrudServiceDataProvider<>(this.methodEquipmentService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<MethodEquipmentEntity> methodEquipmentFromBackend = this.methodEquipmentService.get(event.getValue().getId());
                if (methodEquipmentFromBackend.isPresent()) {
                    populateForm(methodEquipmentFromBackend.get());
                } else {
                    refreshGrid(grid);
                }
            } else {
                clearForm();
            }
        });

        setBinder(new BeanValidationBinder<>(MethodEquipmentEntity.class));
        getBinder().bindInstanceFields(this);

        getCancel().addClickListener(event -> {
            clearForm();
            refreshGrid(grid);
        });

        getSave().addClickListener(event -> {
            try {
                if (this.methodEquipmentEntity == null) {
                    this.methodEquipmentEntity = new MethodEquipmentEntity();
                }
                getBinder().writeBean(this.methodEquipmentEntity);

                this.methodEquipmentService.update(this.methodEquipmentEntity);
                clearForm();
                refreshGrid(grid);
                Notification.show(ITEM_SAVED);
            } catch (ValidationException validationException) {
                Notification.show(ITEM_SAVE_FAIL);
            }
        });

        getDelete().addClickListener(event -> {
            if (this.methodEquipmentEntity != null && this.methodEquipmentEntity.getId() != null) {
                ConfirmationDialog confirmationDialog = new ConfirmationDialog(ITEM_DELETION,
                        ITEM_DELETION_CONFIRMATION + this.methodEquipmentEntity.getId() + " ?", confirm -> {
                    this.methodEquipmentService.delete(this.methodEquipmentEntity.getId());
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
        equipment = new ComboBox<>("Оборудование");
        equipment.setDataProvider(new CrudServiceDataProvider<>(this.equipmentService));
        method = new ComboBox<>("Метод");
        method.setDataProvider(new CrudServiceDataProvider<>(this.methodService));
        Component[] fields = new Component[]{equipment, method};

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
        this.methodEquipmentEntity = (MethodEquipmentEntity) entity;
        getBinder().readBean(this.methodEquipmentEntity);
    }

    @Override
    public BeanValidationBinder<MethodEquipmentEntity> getBinder() {
        return ((BeanValidationBinder<MethodEquipmentEntity>) super.getBinder());
    }
}
