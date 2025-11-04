package pl.jakubholik90.menus;

import java.util.ArrayList;

public class MenuTable {

    private final String title;
    private final ArrayList<MenuItem> menuItems;

    public MenuTable(String title) {
        this.title = title;
        this.menuItems = new ArrayList<>();
    }

    public void addMenuItem(MenuItem menuItem) {
        this.menuItems.add(menuItem);
    }

    public MenuItem getMenuItemById(int id) {
        for (MenuItem menuItem : menuItems) {
            if (menuItem.id() == id) {
                return menuItem;
            }
        }
        return null;
    }

    public int getMenuSize() {
        return this.menuItems.size();
    }

    public String getTitle() {
        return title;
    }


}
