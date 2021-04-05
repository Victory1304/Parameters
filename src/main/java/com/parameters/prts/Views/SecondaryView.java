package com.parameters.prts.Views;

import com.parameters.prts.Model.SecondaryEntity;
import com.parameters.prts.Service.SecondaryService;
import com.parameters.prts.Views.main.MainView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "secondary", layout = MainView.class)
@PageTitle("Вторичные")
@CssImport("./views/secondary/secondary-view.css")
public class SecondaryView extends VerticalLayout {
    private final SecondaryService secondaryService;

    private final Grid<SecondaryEntity> grid = new Grid<>(SecondaryEntity.class);

    public SecondaryView(SecondaryService secondaryService) {
        this.secondaryService = secondaryService;
        addClassName("secondary-view.css");

        add(grid);
    }
}
