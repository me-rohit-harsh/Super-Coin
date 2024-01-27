/**
 * 
 */

const mobileScreen = window.matchMedia("(max-width: 990px )");
$(document).ready(function () {
  $(".dashboard-nav-dropdown-toggle").click(function () {
    $(this).closest(".dashboard-nav-dropdown")
      .toggleClass("show")
      .find(".dashboard-nav-dropdown")
      .removeClass("show");
    $(this).parent()
      .siblings()
      .removeClass("show");
  });
  $(".menu-toggle").click(function () {
    if (mobileScreen.matches) {
      $(".dashboard-nav").toggleClass("mobile-show");
    } else {
      $(".dashboard").toggleClass("dashboard-compact");
    }
  });
});
//alert js

function closeAlert(element) {
  const alert = element.closest('.alert');
  alert.style.animation = "shiftUp 0.5s ease-in-out forwards, fadeOut 0.5s ease-in-out forwards";

  setTimeout(() => {
    alert.style.display = "none";
    alert.remove();
  }, 500);
}

//table js starts 
$(document).ready(function () {
  var table = $('#example').DataTable({
    // "columnDefs": [{
    //   "orderable": false,
    //   "targets": 0
    // }],
    language: {
      'paginate': {
        'previous': '<span class="fa fa-chevron-left"></span>',
        'next': '<span class="fa fa-chevron-right"></span>'
      },
      "lengthMenu": '<button class="btn btn-secondary buttons-print" tabindex="0" aria-controls="example1" type="button">Download Data</button>' +
        '<span> </span>' +
        ' <select class="form-control input-sm mb-2">' +
        '<option value="10">10</option>' +
        '<option value="20">20</option>' +
        '<option value="30">30</option>' +
        '<option value="40">40</option>' +
        '<option value="50">50</option>' +
        '<option value="-1">All</option>' +
        '</select> Total  Result'


    }
  });

  var columnsVisible = false;

  if ($(window).width() <= 767) {

    table.columns(':gt(0)').visible(false);

    $('#example tbody').on('click', 'td:first-child', function () {
      if (!columnsVisible) {
        table.columns(':gt(0)').visible(true);
      } else {
        table.columns(':gt(0)').visible(false);
      }

      columnsVisible = !columnsVisible;
    });
  }


});
// table js ends