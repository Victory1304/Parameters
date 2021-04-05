package com.parameters.prts.Views;

import com.parameters.prts.Model.VidEntity;
import com.parameters.prts.Service.VidService;
import com.parameters.prts.Views.main.MainView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "vid", layout = MainView.class)
@PageTitle("Вид")
@CssImport("./views/vid/vid-view.css")
public class VidView extends VerticalLayout {
    private final VidService vidService;

    private final Grid<VidEntity> grid = new Grid<>(VidEntity.class);

    public VidView(VidService vidService) {
        this.vidService = vidService;
        addClassName("vid-view.css");

        add(grid);
    }
}
