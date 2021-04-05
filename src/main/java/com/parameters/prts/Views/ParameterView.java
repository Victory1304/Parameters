package com.parameters.prts.Views;

import com.parameters.prts.Views.components.ConfirmationDialog;
import com.parameters.prts.Views.main.MainView;
import com.parameters.prts.Model.ParameterEntity;
import com.parameters.prts.Service.ParameterService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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

@Route(value = "parameter", layout = MainView.class)
@PageTitle("Параметры")
@CssImport("./views/parameter/parameter-view.css")
public class ParameterView extends Div {
    private final ParameterService parameterService;

    private final Grid<ParameterEntity> grid = new Grid<>(ParameterEntity.class, false);

    private TextField vid;
    private TextField edinIzmeren;
    private TextField opisanieFp;
    private TextField oblastPrimenen;
    private TextField nameFp;
    private TextField refer;

    private Button cancel = new Button("Отмена");
    private Button save = new Button("Сохранить");
    private Button delete = new Button("Удалить");

    private BeanValidationBinder<ParameterEntity> binder;

    private ParameterEntity parameter;

    public ParameterView(ParameterService parameterService) {
        this.parameterService = parameterService;
        addClassName("parameter-view");

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);
        add(splitLayout);

        grid.addColumn(ParameterEntity::getVid).setHeader("Вид").setAutoWidth(true);
        grid.addColumn(ParameterEntity::getEdinIzmeren).setHeader("Единица измерения").setAutoWidth(true);
        grid.addColumn(ParameterEntity::getOblastPrimenen).setHeader("Область применения").setAutoWidth(true);
        grid.addColumn(ParameterEntity::getNameFP).setHeader("Название ФП").setAutoWidth(true);
        grid.addColumn(ParameterEntity::getOpisanieFP).setHeader("Описание ФП").setAutoWidth(true);
        grid.addColumn(ParameterEntity::getRefer).setHeader("Ссылка").setAutoWidth(true);

        grid.setDataProvider(new CrudServiceDataProvider<>(this.parameterService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<ParameterEntity> parameterFromBackend = this.parameterService.get(event.getValue().getId());
                if (parameterFromBackend.isPresent()) {
                    populateForm(parameterFromBackend.get());
                } else {
                    refreshGrid();
                }
            } else {
                clearForm();
            }
        });

        binder = new BeanValidationBinder<>(ParameterEntity.class);
        binder.bindInstanceFields(this);

        cancel.addClickListener(event -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(event -> {
            try {
                if (this.parameter == null) {
                    this.parameter = new ParameterEntity();
                }
                binder.writeBean(this.parameter);

                this.parameterService.update(this.parameter);
                clearForm();
                refreshGrid();
                Notification.show("Параметр сохранён");
            } catch (ValidationException validationException) {
                Notification.show("Не удалось сохранить :(");
            }
        });

        delete.addClickListener(event -> {
            if (this.parameter != null && this.parameter.getId() != null) {
                ConfirmationDialog confirmationDialog = new ConfirmationDialog("Удаление объекта",
                        "Вы действительно хотите удалить объект " + this.parameter.getNameFP() + " ?", confirm -> {
                    this.parameterService.delete(this.parameter.getId());
                    clearForm();
                    refreshGrid();
                    Notification.show("Параметр удалён");
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
        vid = new TextField("Вид");
        edinIzmeren = new TextField("Единица Измерения");
        oblastPrimenen = new TextField("Область применения");
        nameFp = new TextField("Название ФП");
        opisanieFp = new TextField("Описание ФП");
        refer = new TextField("Ссылка");
        Component[] fields = new Component[]{vid, edinIzmeren, oblastPrimenen, nameFp, opisanieFp, refer};

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

    private void populateForm(ParameterEntity parameterEntity) {
        this.parameter = parameterEntity;
        binder.readBean(this.parameter);
    }

}
