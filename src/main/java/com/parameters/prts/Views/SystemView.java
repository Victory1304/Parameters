package com.parameters.prts.Views;

import com.parameters.prts.Model.BaseEntity;
import com.parameters.prts.Model.SystemEntity;
import com.parameters.prts.Service.SystemService;
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

@Route(value = "system", layout = MainView.class)
@PageTitle("Системы")
@CssImport("./views/system/system-view.css")
public class SystemView extends CommonEntityLayout {
    private final static String ITEM_SAVED = "Система сохранена";
    private final static String ITEM_SAVE_FAIL = "Не удалось сохранить";
    private final static String ITEM_DELETION = "Удаление объекта";
    private final static String ITEM_DELETION_CONFIRMATION = "Вы действительно хотите удалить объект ";
    private final static String ITEM_DELETED = "Система удалена";

    private final SystemService systemService;
    private final Grid<SystemEntity> grid = new Grid<>(SystemEntity.class, false);

    private TextField name;

    private SystemEntity systemEntity;

    public SystemView(SystemService systemService) {
        this.systemService = systemService;
        addClassName("system-view");

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        createGridLayout(splitLayout, grid);
        createEditorLayout(splitLayout);
        add(splitLayout);

        grid.addColumn(SystemEntity::getName).setHeader("Название").setAutoWidth(true);

        grid.setDataProvider(new CrudServiceDataProvider<>(this.systemService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<SystemEntity> systemFromBackend = this.systemService.get(event.getValue().getId());
                if (systemFromBackend.isPresent()) {
                    populateForm(systemFromBackend.get());
                } else {
                    refreshGrid(grid);
                }
            } else {
                clearForm();
            }
        });

        setBinder(new BeanValidationBinder<>(SystemEntity.class));
        getBinder().bindInstanceFields(this);

        getCancel().addClickListener(event -> {
            clearForm();
            refreshGrid(grid);
        });

        getSave().addClickListener(event -> {
            try {
                if (this.systemEntity == null) {
                    this.systemEntity = new SystemEntity();
                }
                getBinder().writeBean(this.systemEntity);

                this.systemService.update(this.systemEntity);
                clearForm();
                refreshGrid(grid);
                Notification.show(ITEM_SAVED);
            } catch (ValidationException validationException) {
                Notification.show(ITEM_SAVE_FAIL);
            }
        });

        getDelete().addClickListener(event -> {
            if (this.systemEntity != null && this.systemEntity.getId() != null) {
                ConfirmationDialog confirmationDialog = new ConfirmationDialog(ITEM_DELETION,
                        ITEM_DELETION_CONFIRMATION + this.systemEntity.getName() + " ?", confirm -> {
                    this.systemService.delete(this.systemEntity.getId());
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
        this.systemEntity = (SystemEntity) entity;
        getBinder().readBean(this.systemEntity);
    }

    @Override
    public BeanValidationBinder<SystemEntity> getBinder() {
        return ((BeanValidationBinder<SystemEntity>) super.getBinder());
    }
}
