package com.parameters.prts.Views;

import com.parameters.prts.Model.AdditionalNameEntity;
import com.parameters.prts.Model.BaseEntity;
import com.parameters.prts.Model.BasicNameIndicatorEntity;
import com.parameters.prts.Model.LanguageEntity;
import com.parameters.prts.Service.AdditionalNameService;
import com.parameters.prts.Service.BasicNameIndicatorService;
import com.parameters.prts.Service.LanguageService;
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

@Route(value = "additionalName", layout = MainView.class)
@PageTitle("Дополнительное название")
@CssImport("./views/additionalName/additionalName-view.css")
public class AdditionalNameView extends CommonEntityLayout {
    private final static String ITEM_SAVED = "Название сохранено";
    private final static String ITEM_SAVE_FAIL = "Не удалось сохранить";
    private final static String ITEM_DELETION = "Удаление объекта";
    private final static String ITEM_DELETION_CONFIRMATION = "Вы действительно хотите удалить объект ";
    private final static String ITEM_DELETED = "Название удалено";

    private final AdditionalNameService additionalNameService;
    private final BasicNameIndicatorService basicNameIndicatorService;
    private final LanguageService languageService;
    private final Grid<AdditionalNameEntity> grid = new Grid<>(AdditionalNameEntity.class, false);

    private TextField abbreviation;
    private TextField decodingAbbreviation;
    private ComboBox<LanguageEntity> language;
    private ComboBox<BasicNameIndicatorEntity> indicator;

    private AdditionalNameEntity additionalNameEntity;

    public AdditionalNameView(AdditionalNameService additionalNameService,
                            BasicNameIndicatorService basicNameIndicatorService, LanguageService languageService) {
        this.additionalNameService = additionalNameService;
        this.basicNameIndicatorService = basicNameIndicatorService;
        this.languageService = languageService;
        addClassName("additionalName-view");

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        createGridLayout(splitLayout, grid);
        createEditorLayout(splitLayout);
        add(splitLayout);

        grid.addColumn(AdditionalNameEntity::getAbbreviation).setHeader("Аббревиатура").setAutoWidth(true);
        grid.addColumn(AdditionalNameEntity::getDecodingAbbreviation).setHeader("Расшифровка").setAutoWidth(true);
        grid.addColumn(AdditionalNameEntity::getIndicator).setHeader("Название индикатора").setAutoWidth(true);
        grid.addColumn(AdditionalNameEntity::getLanguage).setHeader("Формула").setAutoWidth(true);

        grid.setDataProvider(new CrudServiceDataProvider<>(this.additionalNameService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<AdditionalNameEntity> additionalNameFromBackend = this.additionalNameService.get(event.getValue().getId());
                if (additionalNameFromBackend.isPresent()) {
                    populateForm(additionalNameFromBackend.get());
                } else {
                    refreshGrid(grid);
                }
            } else {
                clearForm();
            }
        });

        setBinder(new BeanValidationBinder<>(AdditionalNameEntity.class));
        getBinder().bindInstanceFields(this);

        getCancel().addClickListener(event -> {
            clearForm();
            refreshGrid(grid);
        });

        getSave().addClickListener(event -> {
            try {
                if (this.additionalNameEntity == null) {
                    this.additionalNameEntity = new AdditionalNameEntity();
                }
                getBinder().writeBean(this.additionalNameEntity);

                this.additionalNameService.update(this.additionalNameEntity);
                clearForm();
                refreshGrid(grid);
                Notification.show(ITEM_SAVED);
            } catch (ValidationException validationException) {
                Notification.show(ITEM_SAVE_FAIL);
            }
        });

        getDelete().addClickListener(event -> {
            if (this.additionalNameEntity != null && this.additionalNameEntity.getId() != null) {
                ConfirmationDialog confirmationDialog = new ConfirmationDialog(ITEM_DELETION,
                        ITEM_DELETION_CONFIRMATION + this.additionalNameEntity.getId() + " ?", confirm -> {
                    this.additionalNameService.delete(this.additionalNameEntity.getId());
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

        abbreviation = new TextField("Аббревиатура");
        decodingAbbreviation = new TextField("Расшифровка");
        FormLayout formLayout = new FormLayout();
        indicator = new ComboBox<>("Индикатор");
        indicator.setDataProvider(new CrudServiceDataProvider<>(this.basicNameIndicatorService));
        language = new ComboBox<>("Язык");
        language.setDataProvider(new CrudServiceDataProvider<>(this.languageService));
        Component[] fields = new Component[]{abbreviation, decodingAbbreviation, indicator, language};

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
        this.additionalNameEntity = (AdditionalNameEntity) entity;
        getBinder().readBean(this.additionalNameEntity);
    }

    @Override
    public BeanValidationBinder<AdditionalNameEntity> getBinder() {
        return ((BeanValidationBinder<AdditionalNameEntity>) super.getBinder());
    }
}
