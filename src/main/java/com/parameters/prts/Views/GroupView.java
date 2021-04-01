package com.parameters.prts.Views;

import com.parameters.prts.Model.GroupEntity;
import com.parameters.prts.Service.GroupService;
import com.parameters.prts.Views.main.MainView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "group", layout = MainView.class)
@PageTitle("Группы")
@CssImport("./views/group/group-view.css")
public class GroupView extends VerticalLayout {
    private final GroupService groupService;

    private final Grid<GroupEntity> grid = new Grid<>(GroupEntity.class);

    public GroupView(GroupService groupService) {
        this.groupService = groupService;
        addClassName("group-view");

        add(grid);
        grid.setItems(groupService.findAll());
    }
}
