/**
 * This serves as an extension to dataTables and replaces the default labels with 
 * 	translations according to a externally defined language.
 * 
 * Make sure that this file gets loaded after the translator and all dataTable-related scripts
 */
__translator.addTranslations(["~eu.dariah.de.colreg.common.view.data_tables.aria.sort_ascending",
                              "~eu.dariah.de.colreg.common.view.data_tables.aria.sort_descending",
                              "~eu.dariah.de.colreg.common.view.data_tables.empty_table",
                              "~eu.dariah.de.colreg.common.view.data_tables.info",
                              "~eu.dariah.de.colreg.common.view.data_tables.info_empty",
                              "~eu.dariah.de.colreg.common.view.data_tables.info_filtered",
                              "~eu.dariah.de.colreg.common.view.data_tables.info_post_fix",
                              "~eu.dariah.de.colreg.common.view.data_tables.info_thousands",
                              "~eu.dariah.de.colreg.common.view.data_tables.length_menu",
                              "~eu.dariah.de.colreg.common.view.data_tables.loading_records",
                              "~eu.dariah.de.colreg.common.view.data_tables.paginate.first",
                              "~eu.dariah.de.colreg.common.view.data_tables.paginate.last",
                              "~eu.dariah.de.colreg.common.view.data_tables.paginate.next",
                              "~eu.dariah.de.colreg.common.view.data_tables.paginate.previous",
                              "~eu.dariah.de.colreg.common.view.data_tables.processing",
                              "~eu.dariah.de.colreg.common.view.data_tables.search",
                              "~eu.dariah.de.colreg.common.view.data_tables.zero_records"]);
__translator.getTranslations();

$.fn.dataTable.defaults.oLanguage.oAria.sSortAscending = __translator.translate("~eu.dariah.de.colreg.common.view.data_tables.aria.sort_ascending");
$.fn.dataTable.defaults.oLanguage.oAria.sSortDescending = __translator.translate("~eu.dariah.de.colreg.common.view.data_tables.aria.sort_descending");
$.fn.dataTable.defaults.oLanguage.oPaginate.sFirst = __translator.translate("~eu.dariah.de.colreg.common.view.data_tables.paginate.first");
$.fn.dataTable.defaults.oLanguage.oPaginate.sLast = __translator.translate("~eu.dariah.de.colreg.common.view.data_tables.paginate.last");
$.fn.dataTable.defaults.oLanguage.oPaginate.sNext = __translator.translate("~eu.dariah.de.colreg.common.view.data_tables.paginate.next");
$.fn.dataTable.defaults.oLanguage.oPaginate.sPrevious = __translator.translate("~eu.dariah.de.colreg.common.view.data_tables.paginate.previous");
$.fn.dataTable.defaults.oLanguage.sEmptyTable = __translator.translate("~eu.dariah.de.colreg.common.view.data_tables.empty_table");
$.fn.dataTable.defaults.oLanguage.sInfo = __translator.translate("~eu.dariah.de.colreg.common.view.data_tables.info");
$.fn.dataTable.defaults.oLanguage.sInfoEmpty = __translator.translate("~eu.dariah.de.colreg.common.view.data_tables.info_empty");
$.fn.dataTable.defaults.oLanguage.sInfoFiltered = __translator.translate("~eu.dariah.de.colreg.common.view.data_tables.info_filtered");
$.fn.dataTable.defaults.oLanguage.sInfoPostFix = __translator.translate("~eu.dariah.de.colreg.common.view.data_tables.info_post_fix");
$.fn.dataTable.defaults.oLanguage.sInfoThousands = __translator.translate("~eu.dariah.de.colreg.common.view.data_tables.info_thousands");
$.fn.dataTable.defaults.oLanguage.sLengthMenu = __translator.translate("~eu.dariah.de.colreg.common.view.data_tables.length_menu");
$.fn.dataTable.defaults.oLanguage.sLoadingRecords = __translator.translate("~eu.dariah.de.colreg.common.view.data_tables.loading_records");
$.fn.dataTable.defaults.oLanguage.sProcessing = __translator.translate("~eu.dariah.de.colreg.common.view.data_tables.processing");
$.fn.dataTable.defaults.oLanguage.sSearch = __translator.translate("~eu.dariah.de.colreg.common.view.data_tables.search");
$.fn.dataTable.defaults.oLanguage.sZeroRecords = __translator.translate("~eu.dariah.de.colreg.common.view.data_tables.zero_records");