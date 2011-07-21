package gu.client.view;
import com.smartgwt.client.data.Criteria;  
import com.smartgwt.client.data.DataSource;  
import com.smartgwt.client.data.XJSONDataSource;
import com.smartgwt.client.types.VisibilityMode;  
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;  
import com.smartgwt.client.widgets.form.fields.FilterCriteriaFunction;  
import com.smartgwt.client.widgets.grid.ListGridRecord;  
import com.smartgwt.client.widgets.grid.events.*;  
import com.smartgwt.client.widgets.layout.HLayout;  
import com.smartgwt.client.widgets.layout.SectionStack;  
import com.smartgwt.client.widgets.layout.SectionStackSection;  
import com.smartgwt.client.widgets.menu.Menu;  
import com.smartgwt.client.widgets.menu.MenuItem;  
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;  
import com.smartgwt.client.widgets.tree.events.NodeClickEvent;  
import com.smartgwt.client.widgets.tree.events.NodeClickHandler;  
import gu.client.miniapp.CategoryTreeGrid;
import gu.client.miniapp.HelpPane;
import gu.client.miniapp.ItemDetailTabPane;
import gu.client.miniapp.ItemListGrid;
import gu.client.miniapp.SearchForm;
//import gu.client.miniapp.*;
  
public class Application extends HLayout {  
    private SearchForm searchForm;  
    private CategoryTreeGrid categoryTree;  
    private ItemListGrid itemList;  
    private ItemDetailTabPane itemDetailTabPane;  
    private Menu itemListMenu;  
  
    public Application() {  
        setWidth100();  
        setHeight100();  
        setLayoutMargin(20);  
  
        DataSource supplyCategoryDS = SupplyCategoryXmlDS.getInstance();  
        //DataSource supplyItemDS = ItemSupplyXmlDS.getInstance();  
        DataSource supplyItemDS = ItemSupplyXmlDS.getInstance();  
         
        categoryTree = new CategoryTreeGrid(supplyCategoryDS);  
        categoryTree.setAutoFetchData(true);  
        categoryTree.addNodeClickHandler(new NodeClickHandler() {  
            public void onNodeClick(NodeClickEvent event) {  
                String category = event.getNode().getAttribute("categoryName");  
                findItems(category);  
            }  
        });  
  
        searchForm = new SearchForm(supplyItemDS);  
  
        //when showing options in the combo-box, only show the options from the selected category if appropriate  
        final ComboBoxItem itemNameCB = searchForm.getItemNameField();  
        itemNameCB.setPickListFilterCriteriaFunction(new FilterCriteriaFunction() {  
            public Criteria getCriteria() {  
                ListGridRecord record = categoryTree.getSelectedRecord();  
                if ((itemNameCB.getValue() != null) && record != null) {  
                    Criteria criteria = new Criteria();  
                    criteria.addCriteria("category", record.getAttribute("categoryName"));  
                    return criteria;  
                }  
                return null;  
            }  
        });  
  
        setupContextMenu();  
  
        itemList = new ItemListGrid(supplyItemDS);  
        itemList.addRecordClickHandler(new RecordClickHandler() {  
            public void onRecordClick(RecordClickEvent event) {  
                itemDetailTabPane.updateDetails();  
            }  
        });  
  
        itemList.addCellSavedHandler(new CellSavedHandler() {  
            public void onCellSaved(CellSavedEvent event) {  
                itemDetailTabPane.updateDetails();  
            }  
        });  
  
        itemList.addCellContextClickHandler(new CellContextClickHandler() {  
            public void onCellContextClick(CellContextClickEvent event) {  
                itemListMenu.showContextMenu();  
                event.cancel();  
            }  
        });  
  
  
        SectionStack leftSideLayout = new SectionStack();  
        leftSideLayout.setWidth(280);  
        leftSideLayout.setShowResizeBar(true);  
        leftSideLayout.setVisibilityMode(VisibilityMode.MULTIPLE);  
        leftSideLayout.setAnimateSections(true);  
  
        SectionStackSection suppliesCategorySection = new SectionStackSection("Office Supply Categories");  
        suppliesCategorySection.setExpanded(true);  
        suppliesCategorySection.setItems(categoryTree);  
  
        SectionStackSection instructionsSection = new SectionStackSection("Instructions");  
        instructionsSection.setItems(new HelpPane());  
        instructionsSection.setExpanded(true);  
  
        leftSideLayout.setSections(suppliesCategorySection, instructionsSection);  
  
        SectionStack rightSideLayout = new SectionStack();  
        rightSideLayout.setVisibilityMode(VisibilityMode.MULTIPLE);  
        rightSideLayout.setAnimateSections(true);  
  
  
        searchForm.setHeight(60);  
        searchForm.addFindListener(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {  
            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {  
                findItems(null);  
            }  
        });  
  
        SectionStackSection findSection = new SectionStackSection("Find Items");  
        findSection.setItems(searchForm);  
        findSection.setExpanded(true);  
  
        SectionStackSection supplyItemsSection = new SectionStackSection("Office Supply Items");  
        supplyItemsSection.setItems(itemList);  
        supplyItemsSection.setExpanded(true);  
  
        itemDetailTabPane = new ItemDetailTabPane(supplyItemDS, supplyCategoryDS, itemList);  
        SectionStackSection itemDetailsSection = new SectionStackSection("Item Details");  
        itemDetailsSection.setItems(itemDetailTabPane);  
        itemDetailsSection.setExpanded(true);  
  
        rightSideLayout.setSections(findSection, supplyItemsSection, itemDetailsSection);  
  
        addMember(leftSideLayout);  
        addMember(rightSideLayout);  
        draw();  
    }  
  
    private void setupContextMenu() {  
        itemListMenu = new Menu();  
        itemListMenu.setCellHeight(22);  
  
        MenuItem detailsMenuItem = new MenuItem("Show Details", "silk/application_form.png");  
        detailsMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {  
            public void onClick(MenuItemClickEvent event) {  
                itemDetailTabPane.selectTab(0);  
                itemDetailTabPane.updateDetails();  
            }  
        });  
  
        final MenuItem editMenuItem = new MenuItem("Edit Item", "demoApp/icon_edit.png");  
        editMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {  
            public void onClick(MenuItemClickEvent event) {  
                itemDetailTabPane.selectTab(1);  
                itemDetailTabPane.updateDetails();  
            }  
        });  
  
        final MenuItem deleteMenuItem = new MenuItem("Delete Item", "silk/delete.png");  
        deleteMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {  
            public void onClick(MenuItemClickEvent event) {  
                itemList.removeSelectedData();  
                itemDetailTabPane.clearDetails(null);  
            }  
        });  
  
        itemListMenu.setData(detailsMenuItem, editMenuItem, deleteMenuItem);  
    }  
  
  
    public void findItems(String categoryName) {  
  
        Criteria findValues;  
  
        String formValue = searchForm.getValueAsString("findInCategory");  
        ListGridRecord selectedCategory = categoryTree.getSelectedRecord();  
        if (formValue != null && selectedCategory != null) {  
            categoryName = selectedCategory.getAttribute("categoryName");  
            findValues = searchForm.getValuesAsCriteria();  
            findValues.addCriteria("category", categoryName);  
  
        } else if (categoryName == null) {  
            findValues = searchForm.getValuesAsCriteria();  
        } else {  
            findValues = new Criteria();  
            findValues.addCriteria("category", categoryName);  
        }  
  
        itemList.filterData(findValues);  
        itemDetailTabPane.clearDetails(categoryTree.getSelectedRecord());  
    }  
  
}  