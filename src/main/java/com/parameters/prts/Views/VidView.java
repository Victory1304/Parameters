package com.parameters.prts.Views;

import com.parameters.prts.Model.ParameterEntity;
import com.parameters.prts.Model.PrimaryEntity;
import com.parameters.prts.Model.SecondaryEntity;
import com.parameters.prts.Model.VidEntity;
import com.parameters.prts.Service.ParameterService;
import com.parameters.prts.Service.PrimaryService;
import com.parameters.prts.Service.SecondaryService;
import com.parameters.prts.Service.VidService;
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
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.artur.helpers.CrudServiceDataProvider;

import java.util.Optional;

@Route(value = "vid", layout = MainView.class)
@PageTitle("Вид")
@CssImport("./views/vid/vid-view.css")
public class VidView extends VerticalLayout {
    private final VidService vidService;
    private final ParameterService parameterService;
    private final PrimaryService primaryService;
    private final SecondaryService secondaryService;
    private final Grid<VidEntity> grid = new Grid<>(VidEntity.class, false);

    private ComboBox<ParameterEntity> parameter;
    private ComboBox<PrimaryEntity> primary;
    private ComboBox<SecondaryEntity> secondary;

    private Button cancel = new Button("Отмена");
    private Button save = new Button("Сохранить");
    private Button delete = new Button("Удалить");

    private BeanValidationBinder<VidEntity> binder;

    private VidEntity vid;

    public VidView(VidService vidService, ParameterService parameterService, PrimaryService primaryService, SecondaryService secondaryService) {
        this.vidService = vidService;
        this.parameterService = parameterService;
        this.primaryService = primaryService;
        this.secondaryService = secondaryService;
        addClassName("vid-view");
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);
        add(splitLayout);

        grid.addColumn(VidEntity::getParameter).setHeader("Параметр").setAutoWidth(true);
        grid.addColumn(VidEntity::getPrimary).setHeader("Первичный").setAutoWidth(true);
        grid.addColumn(VidEntity::getSecondary).setHeader("Вторичный").setAutoWidth(true);

        grid.setDataProvider(new CrudServiceDataProvider<>(this.vidService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<VidEntity> vidFromBackend = this.vidService.get(event.getValue().getId());
                if (vidFromBackend.isPresent()) {
                    populateForm(vidFromBackend.get());
                } else {
                    refreshGrid();
                }
            } else {
                clearForm();
            }
        });

        binder = new BeanValidationBinder<>(VidEntity.class);
        binder.bindInstanceFields(this);

        cancel.addClickListener(event -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(event -> {
            try {
                if (this.vid == null) {
                    this.vid = new VidEntity();
                }
                binder.writeBean(this.vid);

                this.vidService.update(this.vid);
                clearForm();
                refreshGrid();
                Notification.show("Вид сохранён");
            } catch (ValidationException validationException) {
                Notification.show("Не удалось сохранить :(");
            }
        });

        delete.addClickListener(event -> {
            if (this.vid != null && this.vid.getId() != null) {
                ConfirmationDialog confirmationDialog = new ConfirmationDialog("Удаление объекта",
                        "Вы действительно хотите удалить объект " + this.vid.getId() + " ?", confirm -> {
                    this.vidService.delete(this.vid.getId());
                    clearForm();
                    refreshGrid();
                    Notification.show("Вид удалён");
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
        parameter = new ComboBox<>("Параметр");
        parameter.setDataProvider(new CrudServiceDataProvider<>(this.parameterService));
        primary = new ComboBox<>("Первичный");
        primary.setDataProvider(new CrudServiceDataProvider<>(this.primaryService));
        secondary = new ComboBox<>("Вторичный");
        secondary.setDataProvider(new CrudServiceDataProvider<>(this.secondaryService));
        Component[] fields = new Component[]{parameter, primary, secondary};

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

    private void populateForm(VidEntity vidEntity) {
        this.vid = vidEntity;
        binder.readBean(this.vid);
    }
}
