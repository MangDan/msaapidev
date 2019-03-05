/**
 * @license
 * Copyright (c) 2014, 2019, Oracle and/or its affiliates.
 * The Universal Permissive License (UPL), Version 1.0
 */
/*
 * Your dashboard ViewModel code goes here
 */
define(['ojs/ojcore', 'knockout', 'jquery', 'ojs/ojknockout', 'ojs/ojlistview', 'ojs/ojmodel', 'ojs/ojgauge', 'ojs/ojbutton', 'ojs/ojcheckboxset', 'ojs/ojselectcombobox', 'ojs/ojpagingcontrol', 'ojs/ojcollectiontabledatasource', 'ojs/ojpagingtabledatasource'],
  function (oj, ko, $) {

    function DashboardViewModel() {
      var self = this;
      // Below are a set of the ViewModel methods invoked by the oj-module component.
      // Please reference the oj-module jsDoc for additional information.

      /**
       * Optional ViewModel method invoked after the View is inserted into the
       * document DOM.  The application can put logic that requires the DOM being
       * attached here. 
       * This method might be called multiple times - after the View is created 
       * and inserted into the DOM and after the View is reconnected 
       * after being disconnected.
       */
      self.connected = function () {
        // Implement if needed
      };

      /**
       * Optional ViewModel method invoked after the View is disconnected from the DOM.
       */
      self.disconnected = function () {
        // Implement if needed
      };

      /**
       * Optional ViewModel method invoked after transition to the new View is complete.
       * That includes any possible animation between the old and the new View.
       */
      self.transitionCompleted = function () {
        // Implement if needed
      };

      var criteriaMap = {};
      criteriaMap['lh'] = {
        key: 'price',
        direction: 'ascending'
      };
      criteriaMap['hl'] = {
        key: 'price',
        direction: 'descending'
      };
      criteriaMap['reviews'] = {
        key: 'reviews',
        direction: 'descending'
      };
      criteriaMap['date'] = {
        key: 'pubdate',
        direction: 'ascending'
      };

      var filters = ['lt30', '30to40', '40to50', 'gt50', 'five', 'four', 'three', 'two', 'dcoward', 'jbrock', 'hschildt', 'jmanico', 'mnaftalin'];

      var filterFunc = {};
      filterFunc['lt30'] = function (model) {
        return (parseFloat(model.get('price')) < 10000);
      };
      filterFunc['30to40'] = function (model) {
        return (parseFloat(model.get('price')) > 10000 && parseFloat(model.get('price')) < 30000);
      };
      filterFunc['40to50'] = function (model) {
        return (parseFloat(model.get('price')) >= 30000 && parseFloat(model.get('price')) <= 50000);
      };
      filterFunc['gt50'] = function (model) {
        return (parseFloat(model.get('price')) > 50000);
      };

      filterFunc['five'] = function (model) {
        return (parseFloat(model.get('ratings')) == 5);
      };
      filterFunc['four'] = function (model) {
        return (parseFloat(model.get('ratings')) >= 4);
      };
      filterFunc['three'] = function (model) {
        return (parseFloat(model.get('ratings')) >= 3);
      };
      filterFunc['two'] = function (model) {
        return (parseFloat(model.get('ratings')) < 3);
      };

      var converterFactory = oj.Validation.converterFactory("number");
      var currencyOptions = {
        style: "currency",
        currency: "KRW",
        currencyDisplay: "symbol"
      };
      this.currencyConverter = converterFactory.createConverter(currencyOptions);

      var model = oj.Model.extend({
        idAttribute: 'ID'
      });

      this.collection = new oj.Collection(null, {
        //url: 'http://132.145.161.244:8011/books',
        url: 'http://132.145.161.244:8011/book',
        model: model
      });
      var originalCollection = this.collection;

      this.dataSource = ko.observable(new oj.PagingTableDataSource(new oj.CollectionTableDataSource(this.collection)));

      this.currentPrice = [];
      this.currentAuthor = [];
      this.currentRating = [];
      this.currentSort = ko.observable("default");

      var self = this;
      this.handleSortCriteriaChanged = function (event, ui) {
        var criteria = criteriaMap[event.detail.value];
        self.dataSource().sort(criteria);
      };

      this.handleFilterChanged = function (event, ui) {
        var value = event.detail.value;
        if (!Array.isArray(value)) {
          return;
        }

        var results = [];
        var processed = false;

        $.each(filters, function (index, filter) {
          if (value.indexOf(filter) > -1) {
            results = results.concat(originalCollection.filter(filterFunc[filter]));
            processed = true;
          }
        });

        if (processed) {
          self.collection = new oj.Collection(results);
        } else {
          self.collection = originalCollection;
        }
        self.dataSource(new oj.PagingTableDataSource(new oj.CollectionTableDataSource(self.collection)));

        if (self.currentSort() != "default") {
          var criteria = criteriaMap[self.currentSort()];
          self.dataSource().sort(criteria);
        }
      };
    }

    /*
     * Returns a constructor for the ViewModel so that the ViewModel is constructed
     * each time the view is displayed.  Return an instance of the ViewModel if
     * only one instance of the ViewModel is needed.
     */
    return new DashboardViewModel();
  }
);
