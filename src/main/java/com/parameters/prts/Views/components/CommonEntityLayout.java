package com.parameters.prts.Views.components;

import com.parameters.prts.Model.BaseEntity;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;

public abstract class CommonEntityLayout extends VerticalLayout {
    private BeanValidationBinder<? extends BaseEntity> binder;
    private Button cancel = new Button("Отмена");
    private Button save = new Button("Сохранить");
    private Button delete = new Button("Удалить");

    public CommonEntityLayout() {
    }

    public BeanValidationBinder<? extends BaseEntity> getBinder() {
        return binder;
    }

    public void setBinder(BeanValidationBinder<? extends BaseEntity> binder) {
        this.binder = binder;
    }

    public Button getCancel() {
        return cancel;
    }

    public Button getSave() {
        return save;
    }

    public Button getDelete() {
        return delete;
    }

    public void createEditorLayout(SplitLayout splitLayout) {

    }

    public void createButtonLayout(Div editorLayoutDiv) {
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

    public void createGridLayout(SplitLayout splitLayout, Grid<? extends BaseEntity> grid) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    public void refreshGrid(Grid<? extends BaseEntity> grid) {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    public void clearForm() {
        populateForm(null);
    }

    public <T extends BaseEntity> void populateForm(T entity) {
    }
}
