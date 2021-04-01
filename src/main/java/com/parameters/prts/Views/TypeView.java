package com.parameters.prts.Views;

import com.parameters.prts.Model.TypeEntity;
import com.parameters.prts.Service.TypeService;
import com.parameters.prts.Views.main.MainView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "type", layout = MainView.class)
@PageTitle("Тип")
@CssImport("./views/type/type-view.css")
public class TypeView extends VerticalLayout {
    private final TypeService typeService;

    private final Grid<TypeEntity> grid = new Grid<>(TypeEntity.class);

    public TypeView(TypeService typeService) {
        this.typeService = typeService;
        addClassName("type-view.css");

        add(grid);
        grid.setItems(typeService.findAll());
    }
}
