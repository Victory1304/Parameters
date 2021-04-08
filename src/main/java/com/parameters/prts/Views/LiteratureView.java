package com.parameters.prts.Views;

import com.parameters.prts.Model.LiteratureEntity;
import com.parameters.prts.Model.ParameterEntity;
import com.parameters.prts.Service.LiteratureService;
import com.parameters.prts.Service.ParameterService;
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

@Route(value = "literature", layout = MainView.class)
@PageTitle("Литература")
@CssImport("./views/literature/literature-view.css")
public class LiteratureView extends VerticalLayout {
    private final LiteratureService literatureService;
    private final ParameterService parameterService;
    private final Grid<LiteratureEntity> grid = new Grid<>(LiteratureEntity.class, false);

    private TextField site;
    private TextField title;
    private ComboBox<ParameterEntity> parameter;

    private Button cancel = new Button("Отмена");
    private Button save = new Button("Сохранить");
    private Button delete = new Button("Удалить");

    private BeanValidationBinder<LiteratureEntity> binder;

    private LiteratureEntity literature;

    public LiteratureView(LiteratureService literatureService, ParameterService parameterService) {
        this.literatureService = literatureService;
        this.parameterService = parameterService;
        addClassName("literature-view");

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);
        add(splitLayout);

        grid.addColumn(LiteratureEntity::getTitle).setHeader("Название").setAutoWidth(true);
        grid.addColumn(LiteratureEntity::getSite).setHeader("Сайт").setAutoWidth(true);
        grid.addColumn(LiteratureEntity::getParameter).setHeader("Параметр").setAutoWidth(true);

        grid.setDataProvider(new CrudServiceDataProvider<>(this.literatureService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<LiteratureEntity> literatureFromBackend = this.literatureService.get(event.getValue().getId());
                if (literatureFromBackend.isPresent()) {
                    populateForm(literatureFromBackend.get());
                } else {
                    refreshGrid();
                }
            } else {
                clearForm();
            }
        });

        binder = new BeanValidationBinder<>(LiteratureEntity.class);
        binder.bindInstanceFields(this);

        cancel.addClickListener(event -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(event -> {
            try {
                if (this.literature == null) {
                    this.literature = new LiteratureEntity();
                }
                binder.writeBean(this.literature);

                this.literatureService.update(this.literature);
                clearForm();
                refreshGrid();
                Notification.show("Литература сохранена");
            } catch (ValidationException validationException) {
                Notification.show("Не удалось сохранить :(");
            }
        });

        delete.addClickListener(event -> {
            if (this.literature != null && this.literature.getId() != null) {
                ConfirmationDialog confirmationDialog = new ConfirmationDialog("Удаление объекта",
                        "Вы действительно хотите удалить объект " + this.literature.getTitle() + " ?", confirm -> {
                    this.literatureService.delete(this.literature.getId());
                    clearForm();
                    refreshGrid();
                    Notification.show("Литература удалена");
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
        site = new TextField("Сайт");
        parameter = new ComboBox<>("Параметр");
        parameter.setDataProvider(new CrudServiceDataProvider<>(this.parameterService));
        Component[] fields = new Component[]{title, site, parameter};

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

    private void populateForm(LiteratureEntity literatureEntity) {
        this.literature = literatureEntity;
        binder.readBean(this.literature);
    }
}
