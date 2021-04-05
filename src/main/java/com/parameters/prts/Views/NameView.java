package com.parameters.prts.Views;

import com.parameters.prts.Model.NameEntity;
import com.parameters.prts.Service.NameService;
import com.parameters.prts.Views.main.MainView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "name", layout = MainView.class)
@PageTitle("Наименования")
@CssImport("./views/name/name-view.css")
public class NameView extends VerticalLayout {
    private final NameService nameService;

    private final Grid<NameEntity> grid = new Grid<>(NameEntity.class);

    public NameView(NameService nameService) {
        this.nameService = nameService;
        addClassName("name-view");

        add(grid);
    }
}
