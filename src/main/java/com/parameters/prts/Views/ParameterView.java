package com.parameters.prts.Views;

import com.parameters.prts.Views.main.MainView;
import com.parameters.prts.Model.ParameterEntity;
import com.parameters.prts.Service.ParameterService;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "parameter", layout = MainView.class)
//@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Параметры")
@CssImport("./views/parameter/parameter-view.css")
public class ParameterView extends VerticalLayout {
    private final ParameterService parameterService;

    private final Grid<ParameterEntity> grid = new Grid<>(ParameterEntity.class);

    public ParameterView(ParameterService parameterService) {
        this.parameterService = parameterService;
        addClassName("parameter-view");

        add(grid);
        grid.setItems(parameterService.findAll());
    }

}
