package com.africa.annauiare.modal.category;

/**
 * Created by ericbasendra on 01/10/16.
 */

public class Category {

    private String categoryname;
    private boolean isChecked;

    public Category(){

    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
