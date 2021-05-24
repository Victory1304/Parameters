package com.parameters.prts.Views;

import com.parameters.prts.Model.BaseEntity;
import com.parameters.prts.Model.GroupSystemEntity;
import com.parameters.prts.Service.GroupSystemService;
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

@Route(value = "groupSystem", layout = MainView.class)
@PageTitle("Группы")
@CssImport("./views/groupSystem/groupSystem-view.css")
public class GroupSystemView extends CommonEntityLayout {
    private final static String ITEM_SAVED = "Группа сохранена";
    private final static String ITEM_SAVE_FAIL = "Не удалось сохранить";
    private final static String ITEM_DELETION = "Удаление объекта";
    private final static String ITEM_DELETION_CONFIRMATION = "Вы действительно хотите удалить объект ";
    private final static String ITEM_DELETED = "Группа удалена";

    private final GroupSystemService groupSystemService;
    private final Grid<GroupSystemEntity> grid = new Grid<>(GroupSystemEntity.class, false);

    private TextField name;

    private GroupSystemEntity groupSystemEntity;

    public GroupSystemView(GroupSystemService groupSystemService) {
        this.groupSystemService = groupSystemService;
        addClassName("groupSystem-view");

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        createGridLayout(splitLayout, grid);
        createEditorLayout(splitLayout);
        add(splitLayout);

        grid.addColumn(GroupSystemEntity::getName).setHeader("Название").setAutoWidth(true);

        grid.setDataProvider(new CrudServiceDataProvider<>(this.groupSystemService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<GroupSystemEntity> groupSystemFromBackend = this.groupSystemService.get(event.getValue().getId());
                if (groupSystemFromBackend.isPresent()) {
                    populateForm(groupSystemFromBackend.get());
                } else {
                    refreshGrid(grid);
                }
            } else {
                clearForm();
            }
        });

        setBinder(new BeanValidationBinder<>(GroupSystemEntity.class));
        getBinder().bindInstanceFields(this);

        getCancel().addClickListener(event -> {
            clearForm();
            refreshGrid(grid);
        });

        getSave().addClickListener(event -> {
            try {
                if (this.groupSystemEntity == null) {
                    this.groupSystemEntity = new GroupSystemEntity();
                }
                getBinder().writeBean(this.groupSystemEntity);

                this.groupSystemService.update(this.groupSystemEntity);
                clearForm();
                refreshGrid(grid);
                Notification.show(ITEM_SAVED);
            } catch (ValidationException validationException) {
                Notification.show(ITEM_SAVE_FAIL);
            }
        });

        getDelete().addClickListener(event -> {
            if (this.groupSystemEntity != null && this.groupSystemEntity.getId() != null) {
                ConfirmationDialog confirmationDialog = new ConfirmationDialog(ITEM_DELETION,
                        ITEM_DELETION_CONFIRMATION + this.groupSystemEntity.getName() + " ?", confirm -> {
                    this.groupSystemService.delete(this.groupSystemEntity.getId());
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
        this.groupSystemEntity = (GroupSystemEntity) entity;
        getBinder().readBean(this.groupSystemEntity);
    }

    @Override
    public BeanValidationBinder<GroupSystemEntity> getBinder() {
        return ((BeanValidationBinder<GroupSystemEntity>) super.getBinder());
    }
}

