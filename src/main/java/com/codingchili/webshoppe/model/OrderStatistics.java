package com.codingchili.webshoppe.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Robin Duda
 */
public class OrderStatistics {
    private List<OrderStatistic> list = new ArrayList<>();

    public void add(OrderStatistic statistic) {
        list.add(statistic);
    }

    public List<OrderStatistic> getList() {
        return list;
    }

    public void setList(List<OrderStatistic> list) {
        this.list = list;
    }

    public int getTotalCost() {
        return list.stream()
                .map(OrderStatistic::getCost)
                .reduce((current, i) -> current + i)
                .orElse(0);
    }

    public int getTotalItems() {
        return list.stream()
                .map(OrderStatistic::getItems)
                .reduce((current, i) -> current + i)
                .orElse(0);
    }

    public int getTotalStatuses() {
        return list.stream()
                .map(OrderStatistic::getCount)
                .reduce((current, i) -> current + i)
                .orElse(0);
    }
}
