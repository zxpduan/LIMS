jQuery(function($) {
	//var help = null;
	
	$(window).on('hashchange.start_help_new', function(e) {
		
		if(help == null && window.location.hash.indexOf('#help')!=-1) {//modify by zxp  			
			help = new ace.Onpage_Help($)
			help.init()
			help.disable();			
			//add #help tag to links to enable help
			$(document).on('click.start_help', '.sidebar .nav-list a', function() {
				var href = $(this).attr('href');
				if( !href.match(/\#help$/) ) $(this).attr('href', href+'#help');
			});
		}
		else if(help != null &&window.location.hash.indexOf('#help')!=-1){			
			help.init()
			help.disable();
		}
	}).triggerHandler('hashchange.start_help_new');
	
	
	//some buttons inside demo pages to launch help
	$(document).on(ace.click_event, '.btn-display-help', function(e) {
		e.preventDefault();
		alert(0);
		if(help == null) {
			help = new ace.Onpage_Help($)
			help.init()
			help.disable();
			
			//
			$('#ace-toggle-onpage-help').trigger('click');
		}
		
		var section = "/lims/"+$(this).attr('href');
		help.show_help(section);
	});
});
