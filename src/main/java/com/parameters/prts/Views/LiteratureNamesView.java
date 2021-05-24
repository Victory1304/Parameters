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

@Route(value = "literatureNames", layout = MainView.class)
@PageTitle("Литература названий")
@CssImport("./views/literatureNames/literatureNames-view.css")
public class LiteratureNamesView extends CommonEntityLayout {
    private final static String ITEM_SAVED = "Связь сохранена";
    private final static String ITEM_SAVE_FAIL = "Не удалось сохранить";
    private final static String ITEM_DELETION = "Удаление связи";
    private final static String ITEM_DELETION_CONFIRMATION = "Вы действительно хотите удалить связь ";
    private final static String ITEM_DELETED = "Связь удалена";

    private final LiteratureNamesService literatureNamesService;
    private final AdditionalNameService additionalNameService;
    private final LiteratureService literatureService;
    private final Grid<LiteratureNamesEntity> grid = new Grid<>(LiteratureNamesEntity.class, false);

    private ComboBox<AdditionalNameEntity> additionalName;
    private ComboBox<LiteratureEntity> literature;

    private LiteratureNamesEntity literatureNamesEntity;

    public LiteratureNamesView(LiteratureNamesService literatureNamesService,
                               AdditionalNameService additionalNameService, LiteratureService literatureService) {
        this.literatureNamesService = literatureNamesService;
        this.additionalNameService = additionalNameService;
        this.literatureService = literatureService;
        addClassName("literatureNames-view");

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        createGridLayout(splitLayout, grid);
        createEditorLayout(splitLayout);
        add(splitLayout);

        grid.addColumn(LiteratureNamesEntity::getAdditionalName).setHeader("Название").setAutoWidth(true);
        grid.addColumn(LiteratureNamesEntity::getLiterature).setHeader("Литература").setAutoWidth(true);

        grid.setDataProvider(new CrudServiceDataProvider<>(this.literatureNamesService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<LiteratureNamesEntity> literatureNamesFromBackend = this.literatureNamesService.get(event.getValue().getId());
                if (literatureNamesFromBackend.isPresent()) {
                    populateForm(literatureNamesFromBackend.get());
                } else {
                    refreshGrid(grid);
                }
            } else {
                clearForm();
            }
        });

        setBinder(new BeanValidationBinder<>(LiteratureNamesEntity.class));
        getBinder().bindInstanceFields(this);

        getCancel().addClickListener(event -> {
            clearForm();
            refreshGrid(grid);
        });

        getSave().addClickListener(event -> {
            try {
                if (this.literatureNamesEntity == null) {
                    this.literatureNamesEntity = new LiteratureNamesEntity();
                }
                getBinder().writeBean(this.literatureNamesEntity);

                this.literatureNamesService.update(this.literatureNamesEntity);
                clearForm();
                refreshGrid(grid);
                Notification.show(ITEM_SAVED);
            } catch (ValidationException validationException) {
                Notification.show(ITEM_SAVE_FAIL);
            }
        });

        getDelete().addClickListener(event -> {
            if (this.literatureNamesEntity != null && this.literatureNamesEntity.getId() != null) {
                ConfirmationDialog confirmationDialog = new ConfirmationDialog(ITEM_DELETION,
                        ITEM_DELETION_CONFIRMATION + this.literatureNamesEntity.getId() + " ?", confirm -> {
                    this.literatureNamesService.delete(this.literatureNamesEntity.getId());
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
        additionalName = new ComboBox<>("Название");
        additionalName.setDataProvider(new CrudServiceDataProvider<>(this.additionalNameService));
        literature = new ComboBox<>("Литература");
        literature.setDataProvider(new CrudServiceDataProvider<>(this.literatureService));
        Component[] fields = new Component[]{additionalName, literature};

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
        this.literatureNamesEntity = (LiteratureNamesEntity) entity;
        getBinder().readBean(this.literatureNamesEntity);
    }

    @Override
    public BeanValidationBinder<LiteratureNamesEntity> getBinder() {
        return ((BeanValidationBinder<LiteratureNamesEntity>) super.getBinder());
    }
}
