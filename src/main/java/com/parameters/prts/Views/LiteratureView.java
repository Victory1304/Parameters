package com.parameters.prts.Views;

import com.parameters.prts.Model.BaseEntity;
import com.parameters.prts.Model.LiteratureEntity;
import com.parameters.prts.Service.LiteratureService;
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

@Route(value = "literature", layout = MainView.class)
@PageTitle("Литература")
@CssImport("./views/literature/literature-view.css")
public class LiteratureView extends CommonEntityLayout {
    private final static String ITEM_SAVED = "Литература сохранена";
    private final static String ITEM_SAVE_FAIL = "Не удалось сохранить";
    private final static String ITEM_DELETION = "Удаление объекта";
    private final static String ITEM_DELETION_CONFIRMATION = "Вы действительно хотите удалить объект ";
    private final static String ITEM_DELETED = "Литература удалена";

    private final LiteratureService literatureService;
    private final Grid<LiteratureEntity> grid = new Grid<>(LiteratureEntity.class, false);

    private TextField website;
    private TextField title;

    private LiteratureEntity literatureEntity;

    public LiteratureView(LiteratureService literatureService) {
        this.literatureService = literatureService;
        addClassName("literature-view");

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        createGridLayout(splitLayout, grid);
        createEditorLayout(splitLayout);
        add(splitLayout);

        grid.addColumn(LiteratureEntity::getTitle).setHeader("Название").setAutoWidth(true);
        grid.addColumn(LiteratureEntity::getWebsite).setHeader("Ссылка").setAutoWidth(true);

        grid.setDataProvider(new CrudServiceDataProvider<>(this.literatureService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<LiteratureEntity> literatureFromBackend = this.literatureService.get(event.getValue().getId());
                if (literatureFromBackend.isPresent()) {
                    populateForm(literatureFromBackend.get());
                } else {
                    refreshGrid(grid);
                }
            } else {
                clearForm();
            }
        });

        setBinder(new BeanValidationBinder<>(LiteratureEntity.class));
        getBinder().bindInstanceFields(this);

        getCancel().addClickListener(event -> {
            clearForm();
            refreshGrid(grid);
        });

        getSave().addClickListener(event -> {
            try {
                if (this.literatureEntity == null) {
                    this.literatureEntity = new LiteratureEntity();
                }
                getBinder().writeBean(this.literatureEntity);

                this.literatureService.update(this.literatureEntity);
                clearForm();
                refreshGrid(grid);
                Notification.show(ITEM_SAVED);
            } catch (ValidationException validationException) {
                Notification.show(ITEM_SAVE_FAIL);
            }
        });

        getDelete().addClickListener(event -> {
            if (this.literatureEntity != null && this.literatureEntity.getId() != null) {
                ConfirmationDialog confirmationDialog = new ConfirmationDialog(ITEM_DELETION,
                        ITEM_DELETION_CONFIRMATION + this.literatureEntity.getTitle() + " ?", confirm -> {
                    this.literatureService.delete(this.literatureEntity.getId());
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
        this.literatureEntity = (LiteratureEntity) entity;
        getBinder().readBean(this.literatureEntity);
    }

    @Override
    public BeanValidationBinder<LiteratureEntity> getBinder() {
        return ((BeanValidationBinder<LiteratureEntity>) super.getBinder());
    }
}
