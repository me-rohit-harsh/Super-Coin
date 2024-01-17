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


function updateSocialMediaLinks() {
    var refLink = document.getElementById('refLinkInput').value;
    var facebookLink = "https://www.facebook.com/sharer.php?u=" + refLink + "&t=Super Coin";
    var whatsappLink = "whatsapp://send?text=*Super Coin* " + refLink;
    var mailtoLink = "mailto:?subject=Super Coin&body=Check out this " + refLink;

    // Update the href attributes
    document.getElementById('facebookShareLink').href = facebookLink;
    document.getElementById('whatsappShareLink').href = whatsappLink;
    document.getElementById('mailtoLink').href = mailtoLink;
}

// Call the function when the document is fully loaded
document.addEventListener("DOMContentLoaded", function() {
    updateSocialMediaLinks();
});





//  clipboard js 

function copyToClipboard() {
	var refLink = document.getElementById('refLinkInput');
	refLink.select();
	document.execCommand('copy');
	document.getElementById('copyNotification').style.display = 'block';
	setTimeout(function() {
		document.getElementById('copyNotification').style.display = 'none';
	}, 3000);
}