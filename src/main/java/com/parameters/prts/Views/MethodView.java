package com.parameters.prts.Views;

import com.parameters.prts.Model.BaseEntity;
import com.parameters.prts.Model.MethodEntity;
import com.parameters.prts.Model.SourceEntity;
import com.parameters.prts.Service.MethodService;
import com.parameters.prts.Service.SourceService;
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
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.artur.helpers.CrudServiceDataProvider;

import java.util.Optional;

@Route(value = "method", layout = MainView.class)
@PageTitle("Методы")
@CssImport("./views/method/method-view.css")
public class MethodView extends CommonEntityLayout {
    private final static String ITEM_SAVED = "Метод сохранён";
    private final static String ITEM_SAVE_FAIL = "Не удалось сохранить";
    private final static String ITEM_DELETION = "Удаление объекта";
    private final static String ITEM_DELETION_CONFIRMATION = "Вы действительно хотите удалить объект ";
    private final static String ITEM_DELETED = "Метод удалён";

    private final MethodService methodService;
    private final SourceService sourceService;
    private final Grid<MethodEntity> grid = new Grid<>(MethodEntity.class, false);

    private TextField nameRu;
    private TextField nameForeign;
    private TextField conditions;
    private TextField restrictions;
    private TextField normalValue;
    private TextField borderlineValue;
    private TextField info;
    private ComboBox<SourceEntity> source;

    private MethodEntity methodEntity;

    public MethodView(MethodService methodService, SourceService sourceService) {
        this.methodService = methodService;
        this.sourceService = sourceService;
        addClassName("method-view");

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        createGridLayout(splitLayout, grid);
        createEditorLayout(splitLayout);
        add(splitLayout);

        grid.addColumn(MethodEntity::getNameRu).setHeader("Название на русском").setAutoWidth(true);
        grid.addColumn(MethodEntity::getNameForeign).setHeader("Иностранное название").setAutoWidth(true);
        grid.addColumn(MethodEntity::getConditions).setHeader("Условия").setAutoWidth(true);
        grid.addColumn(MethodEntity::getRestrictions).setHeader("Ограничения").setAutoWidth(true);
        grid.addColumn(MethodEntity::getNormalValue).setHeader("Нормальное значение").setAutoWidth(true);
        grid.addColumn(MethodEntity::getBorderlineValue).setHeader("Граничное значение").setAutoWidth(true);
        grid.addColumn(MethodEntity::getInfo).setHeader("Информация").setAutoWidth(true);
        grid.addColumn(MethodEntity::getSource).setHeader("Источник").setAutoWidth(true);

        grid.setDataProvider(new CrudServiceDataProvider<>(this.methodService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<MethodEntity> methodFromBackend = this.methodService.get(event.getValue().getId());
                if (methodFromBackend.isPresent()) {
                    populateForm(methodFromBackend.get());
                } else {
                    refreshGrid(grid);
                }
            } else {
                clearForm();
            }
        });

        setBinder(new BeanValidationBinder<>(MethodEntity.class));
        getBinder().forField(normalValue).withConverter(new StringToDoubleConverter("Число"))
                   .bind(MethodEntity::getNormalValue, MethodEntity::setNormalValue);
        getBinder().bindInstanceFields(this);

        getCancel().addClickListener(event -> {
            clearForm();
            refreshGrid(grid);
        });

        getSave().addClickListener(event -> {
            try {
                if (this.methodEntity == null) {
                    this.methodEntity = new MethodEntity();
                }
                getBinder().writeBean(this.methodEntity);

                this.methodService.update(this.methodEntity);
                clearForm();
                refreshGrid(grid);
                Notification.show(ITEM_SAVED);
            } catch (ValidationException validationException) {
                Notification.show(ITEM_SAVE_FAIL);
            }
        });

        getDelete().addClickListener(event -> {
            if (this.methodEntity != null && this.methodEntity.getId() != null) {
                ConfirmationDialog confirmationDialog = new ConfirmationDialog(ITEM_DELETION,
                        ITEM_DELETION_CONFIRMATION + this.methodEntity.getId() + " ?", confirm -> {
                    this.methodService.delete(this.methodEntity.getId());
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

        nameRu = new TextField("Название русское");
        nameForeign = new TextField("Иностранное название");
        conditions = new TextField("Условия");
        restrictions = new TextField("Ограничения");
        normalValue = new TextField("Нормальное значение");
        borderlineValue = new TextField("Граничное значение");
        info = new TextField("Информация");
        source = new ComboBox<>("Источник");
        source.setDataProvider(new CrudServiceDataProvider<>(this.sourceService));
        Component[] fields = new Component[]{
                nameRu, nameForeign, conditions, restrictions, normalValue, borderlineValue, source, info
        };
        FormLayout formLayout = new FormLayout();

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
        this.methodEntity = (MethodEntity) entity;
        getBinder().readBean(this.methodEntity);
    }

    @Override
    public BeanValidationBinder<MethodEntity> getBinder() {
        return ((BeanValidationBinder<MethodEntity>) super.getBinder());
    }
}
