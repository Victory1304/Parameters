package com.parameters.prts.Views;

import com.parameters.prts.Model.BaseEntity;
import com.parameters.prts.Model.SourceEntity;
import com.parameters.prts.Service.SourceService;
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

@Route(value = "source", layout = MainView.class)
@PageTitle("Источники")
@CssImport("./views/source/source-view.css")
public class SourceView extends CommonEntityLayout {
    private final static String ITEM_SAVED = "Источник сохранен";
    private final static String ITEM_SAVE_FAIL = "Не удалось сохранить";
    private final static String ITEM_DELETION = "Удаление объекта";
    private final static String ITEM_DELETION_CONFIRMATION = "Вы действительно хотите удалить объект ";
    private final static String ITEM_DELETED = "Источник удалён";

    private final SourceService sourceService;
    private final Grid<SourceEntity> grid = new Grid<>(SourceEntity.class, false);

    private TextField website;
    private TextField title;

    private SourceEntity sourceEntity;

    public SourceView(SourceService sourceService) {
        this.sourceService = sourceService;
        addClassName("source-view");

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        createGridLayout(splitLayout, grid);
        createEditorLayout(splitLayout);
        add(splitLayout);

        grid.addColumn(SourceEntity::getTitle).setHeader("Название").setAutoWidth(true);
        grid.addColumn(SourceEntity::getWebsite).setHeader("Ссылка").setAutoWidth(true);

        grid.setDataProvider(new CrudServiceDataProvider<>(this.sourceService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<SourceEntity> sourceFromBackend = this.sourceService.get(event.getValue().getId());
                if (sourceFromBackend.isPresent()) {
                    populateForm(sourceFromBackend.get());
                } else {
                    refreshGrid(grid);
                }
            } else {
                clearForm();
            }
        });

        setBinder(new BeanValidationBinder<>(SourceEntity.class));
        getBinder().bindInstanceFields(this);

        getCancel().addClickListener(event -> {
            clearForm();
            refreshGrid(grid);
        });

        getSave().addClickListener(event -> {
            try {
                if (this.sourceEntity == null) {
                    this.sourceEntity = new SourceEntity();
                }
                getBinder().writeBean(this.sourceEntity);

                this.sourceService.update(this.sourceEntity);
                clearForm();
                refreshGrid(grid);
                Notification.show(ITEM_SAVED);
            } catch (ValidationException validationException) {
                Notification.show(ITEM_SAVE_FAIL);
            }
        });

        getDelete().addClickListener(event -> {
            if (this.sourceEntity != null && this.sourceEntity.getId() != null) {
                ConfirmationDialog confirmationDialog = new ConfirmationDialog(ITEM_DELETION,
                        ITEM_DELETION_CONFIRMATION + this.sourceEntity.getTitle() + " ?", confirm -> {
                    this.sourceService.delete(this.sourceEntity.getId());
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
        website = new TextField("Наименование");
        title = new TextField("Аббревиатура");
        Component[] fields = new Component[]{website, title};

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
        this.sourceEntity = (SourceEntity) entity;
        getBinder().readBean(this.sourceEntity);
    }

    @Override
    public BeanValidationBinder<SourceEntity> getBinder() {
        return ((BeanValidationBinder<SourceEntity>) super.getBinder());
    }
}
