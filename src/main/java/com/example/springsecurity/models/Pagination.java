package com.example.springsecurity.models;

public class Pagination {

    private Integer size;

    private Integer page;

    private Integer totalPages;

    private Long totalElements;
    
    private Object todoList;

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Object getTodoList() {
        return todoList;
    }

    public void setTodoList(Object todoList) {
        this.todoList = todoList;
    }



}
