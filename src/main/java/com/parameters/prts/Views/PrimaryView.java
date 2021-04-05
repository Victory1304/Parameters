package com.parameters.prts.Views;

import com.parameters.prts.Model.PrimaryEntity;
import com.parameters.prts.Service.PrimaryService;
import com.parameters.prts.Views.main.MainView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "primary", layout = MainView.class)
@PageTitle("Первичные")
@CssImport("./views/primary/primary-view.css")
public class PrimaryView extends VerticalLayout {
    private final PrimaryService primaryService;

    private final Grid<PrimaryEntity> grid = new Grid<>(PrimaryEntity.class);

    public PrimaryView(PrimaryService primaryService) {
        this.primaryService = primaryService;
        addClassName("primary-view.css");

        add(grid);
    }
}
