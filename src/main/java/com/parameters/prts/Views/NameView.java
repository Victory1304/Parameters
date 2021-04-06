package com.parameters.prts.Views;

import com.parameters.prts.Model.NameEntity;
import com.parameters.prts.Model.ParameterEntity;
import com.parameters.prts.Service.NameService;
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
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.artur.helpers.CrudServiceDataProvider;

import java.util.Optional;

@Route(value = "name", layout = MainView.class)
@PageTitle("Наименования")
@CssImport("./views/name/name-view.css")
public class NameView extends VerticalLayout {
    private final NameService nameService;
    private final ParameterService parameterService;
    private final Grid<NameEntity> grid = new Grid<>(NameEntity.class, false);

    private TextField name;
    private TextField abbr;
    private TextField nvers;
    private ComboBox<ParameterEntity> parameter;

    private Button cancel = new Button("Отмена");
    private Button save = new Button("Сохранить");
    private Button delete = new Button("Удалить");

    private BeanValidationBinder<NameEntity> binder;

    private NameEntity nameEntity;

    public NameView(NameService nameService, ParameterService parameterService) {
        this.nameService = nameService;
        this.parameterService = parameterService;
        addClassName("name-view");

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);
        add(splitLayout);

        grid.addColumn(NameEntity::getName).setHeader("Наименование").setAutoWidth(true);
        grid.addColumn(NameEntity::getAbbr).setHeader("Аббревиатура").setAutoWidth(true);
        grid.addColumn(NameEntity::getNvers).setHeader("Номер версии").setAutoWidth(true);
        grid.addColumn(NameEntity::getParameter).setHeader("Параметр").setAutoWidth(true);

        grid.setDataProvider(new CrudServiceDataProvider<>(this.nameService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<NameEntity> nameFromBackend = this.nameService.get(event.getValue().getId());
                if (nameFromBackend.isPresent()) {
                    populateForm(nameFromBackend.get());
                } else {
                    refreshGrid();
                }
            } else {
                clearForm();
            }
        });

        binder = new BeanValidationBinder<>(NameEntity.class);
        binder.forField(nvers).withConverter(new StringToIntegerConverter("Число"))
                .bind(NameEntity::getNvers, NameEntity::setNvers);
        binder.bindInstanceFields(this);

        cancel.addClickListener(event -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(event -> {
            try {
                if (this.nameEntity == null) {
                    this.nameEntity = new NameEntity();
                }
                binder.writeBean(this.nameEntity);

                this.nameService.update(this.nameEntity);
                clearForm();
                refreshGrid();
                Notification.show("Название сохранено");
            } catch (ValidationException validationException) {
                Notification.show("Не удалось сохранить :(");
            }
        });

        delete.addClickListener(event -> {
            if (this.nameEntity != null && this.nameEntity.getId() != null) {
                ConfirmationDialog confirmationDialog = new ConfirmationDialog("Удаление объекта",
                        "Вы действительно хотите удалить объект " + this.nameEntity.getName() + " ?", confirm -> {
                    this.nameService.delete(this.nameEntity.getId());
                    clearForm();
                    refreshGrid();
                    Notification.show("Название удалено");
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
        name = new TextField("Наименование");
        abbr = new TextField("Аббревиатура");
        nvers = new TextField("Номер версии");
        parameter = new ComboBox<>("Параметр");
        parameter.setDataProvider(new CrudServiceDataProvider<>(this.parameterService));
        Component[] fields = new Component[]{name, abbr, nvers, parameter};

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

    private void populateForm(NameEntity nameEntity) {
        this.nameEntity = nameEntity;
        binder.readBean(this.nameEntity);
    }
}
