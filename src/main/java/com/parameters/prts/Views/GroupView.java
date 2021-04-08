package com.parameters.prts.Views;

import com.parameters.prts.Model.GroupEntity;
import com.parameters.prts.Model.ParameterEntity;
import com.parameters.prts.Model.TypeEntity;
import com.parameters.prts.Service.GroupService;
import com.parameters.prts.Service.ParameterService;
import com.parameters.prts.Service.TypeService;
import com.parameters.prts.Views.components.ConfirmationDialog;
import com.parameters.prts.Views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.artur.helpers.CrudServiceDataProvider;

import java.util.Optional;

@Route(value = "group", layout = MainView.class)
@PageTitle("Группы")
@CssImport("./views/group/group-view.css")
public class GroupView extends VerticalLayout {
    private final GroupService groupService;
    private final TypeService typeService;
    private final Grid<GroupEntity> grid = new Grid<>(GroupEntity.class, false);

    private TextField title;
    private ComboBox<TypeEntity> type;

    private Button cancel = new Button("Отмена");
    private Button save = new Button("Сохранить");
    private Button delete = new Button("Удалить");

    private BeanValidationBinder<GroupEntity> binder;

    private GroupEntity group;

    public GroupView(GroupService groupService, TypeService typeService) {
        this.groupService = groupService;
        this.typeService = typeService;
        addClassName("group-view");

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);
        add(splitLayout);

        grid.addColumn(GroupEntity::getTitle).setHeader("Название").setAutoWidth(true);
        grid.addColumn(GroupEntity::getType).setHeader("Тип").setAutoWidth(true);

        grid.setDataProvider(new CrudServiceDataProvider<>(this.groupService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<GroupEntity> groupFromBackend = this.groupService.get(event.getValue().getId());
                if (groupFromBackend.isPresent()) {
                    populateForm(groupFromBackend.get());
                } else {
                    refreshGrid();
                }
            } else {
                clearForm();
            }
        });

        binder = new BeanValidationBinder<>(GroupEntity.class);
        binder.bindInstanceFields(this);

        cancel.addClickListener(event -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(event -> {
            try {
                if (this.group == null) {
                    this.group = new GroupEntity();
                }
                binder.writeBean(this.group);

                this.groupService.update(this.group);
                clearForm();
                refreshGrid();
                Notification.show("Группа сохранена");
            } catch (ValidationException validationException) {
                Notification.show("Не удалось сохранить :(");
            }
        });

        delete.addClickListener(event -> {
            if (this.group != null && this.group.getId() != null) {
                ConfirmationDialog confirmationDialog = new ConfirmationDialog("Удаление объекта",
                        "Вы действительно хотите удалить объект " + this.group.getTitle() + " ?", confirm -> {
                    this.groupService.delete(this.group.getId());
                    clearForm();
                    refreshGrid();
                    Notification.show("Группа удалена");
                });
                confirmationDialog.open();
            }
        });
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setId("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setId("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        title = new TextField("Название");
        type = new ComboBox<>("Тип");
        type.setDataProvider(new CrudServiceDataProvider<>(this.typeService));
        Component[] fields = new Component[]{title, type};

        for (Component field : fields) {
            ((HasStyle) field).addClassName("full-width");
        }
        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        VerticalLayout buttonLayout = new VerticalLayout();
        buttonLayout.setId("button-layout");
        buttonLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        buttonLayout.setSpacing(true);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        buttonLayout.add(save, delete, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(GroupEntity groupEntity) {
        this.group = groupEntity;
        binder.readBean(this.group);
    }
}
