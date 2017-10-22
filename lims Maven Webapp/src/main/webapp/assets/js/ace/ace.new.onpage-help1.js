jQuery(function($) {
	//var help = null;	
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
	
});
