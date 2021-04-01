package com.parameters.prts.Views;

import com.parameters.prts.Model.LiteratureEntity;
import com.parameters.prts.Service.LiteratureService;
import com.parameters.prts.Views.main.MainView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "literature", layout = MainView.class)
//@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Литература")
@CssImport("./views/literature/literature-view.css")
public class LiteratureView extends VerticalLayout {
    private final LiteratureService literatureService;
    private final Grid<LiteratureEntity> grid = new Grid<>(LiteratureEntity.class);

    public LiteratureView(LiteratureService literatureService) {
        this.literatureService = literatureService;
        addClassName("literature-view");

        add(grid);
        grid.setItems(literatureService.findAll());
    }
}
