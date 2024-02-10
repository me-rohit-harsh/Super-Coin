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
      "lengthMenu": '<span class="download-btn-container"></span>' + // Container for the button
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

  // Append the download button inside the container
  $(document).ready(function () {
    $('.download-btn-container').html('<button class=" text-light btn btn-outline-secondary" id="downloadBtn"> <i class="fas fa-solid fa-download "></i> Download Data</button>');
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



// download button js starts here 


// Event delegation for the download button
$(document).on('click', '#downloadBtn', function () {
  // Get the table element
  var table = document.querySelector('table');

  // Get the table data as CSV format
  var csv = [];
  var rows = table.querySelectorAll('tr');
  for (var i = 0; i < rows.length; i++) {
    var row = [],
      cols = rows[i].querySelectorAll('td, th');
    for (var j = 0; j < cols.length; j++) {
      row.push(cols[j].innerText);
    }
    csv.push(row.join(','));
  }
  var csvContent = "data:text/csv;charset=utf-8," + csv.join("\n");

  // Create a link element and trigger the download
  var encodedUri = encodeURI(csvContent);
  var link = document.createElement("a");
  link.setAttribute("href", encodedUri);
  link.setAttribute("download", "table_data.csv");
  document.body.appendChild(link);
  link.click();
});



// download button js ends here 