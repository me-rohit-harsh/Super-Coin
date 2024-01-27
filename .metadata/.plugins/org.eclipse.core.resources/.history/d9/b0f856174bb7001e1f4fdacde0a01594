/**
 * 
 */

const mobileScreen = window.matchMedia("(max-width: 990px )");
$(document).ready(function() {
	$(".dashboard-nav-dropdown-toggle").click(function() {
		$(this).closest(".dashboard-nav-dropdown")
			.toggleClass("show")
			.find(".dashboard-nav-dropdown")
			.removeClass("show");
		$(this).parent()
			.siblings()
			.removeClass("show");
	});
	$(".menu-toggle").click(function() {
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
