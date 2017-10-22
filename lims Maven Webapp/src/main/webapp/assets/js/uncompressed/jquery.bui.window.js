/*************************************
 * NAME:    jquery.bui.window.js
 * VERSION: 0.0.1
 * DATE:    2013-4-12
 * AUTHOR:  blacklaw
 * HOME:    www.townke.com
 * EMAIL:   2577927146@qq.com
 * DEPENDS: 
 *          jquery-1.9.1.js
 *          jquery-ui.js
 *************************************/
(function($,undefined){
        $.widget('bui.window',{
        options:{
            handler:null,
            lastState:null,
            state:'normal',
            maxButPic:null,
            maxcss:{
                'opacity':1,
                'position':'absolute',
                'left':'0px',
                'top':'0px',
                'width':'100%',
                'height':'100%',
                'background':'#0f0'
            },
            mincss:{
                'left':0,
                'top':0,
                'opacity':.5,           
            },
            normalcss:{
                'opacity':1,
            },
        },
        _create:function(){
            var that=this;
            if(!$.windows) $.windows=[];
            this.options.handler=$.windows.push(this.element);
            //alert(this.options.title);
            this.element.dialog(this.options);
            this.options.normalcss=$.extend(this.options.normalcss,{
                left:this.element.parent().css('left'),
                top:this.element.parent().css('top'),
            });
            this.element.prev().disableSelection().dblclick(function(){that._maxClick();});
            var closeBut=this.element.prev().children("button[title=close]").click(function(){that._destroy();});
            var maxBut=closeBut.clone(true)
                                .children("span:first").removeClass('ui-icon-closethick')
                                .parent().unbind('click').css('right','28px');
            var minBut=maxBut.clone(true).css('right','50px');
            this.options.maxButPic=maxBut.insertBefore(closeBut).click(function(){that._maxClick();})
                    .children("span:first").addClass('ui-icon-bookmark');
            minBut.insertBefore(maxBut).click(function(){that._minClick();})
                    .children("span:first").addClass('ui-icon-minusthick');
            $(window).resize(function(){
                if(that.options.state=='max')
                    that._changeState('max');
                });
        },
        _minClick:function(){
            if(this.options.state!='min'){
                this.options.lastState=this.options.state;
                //
                this._changeState('min');
            }else{
                this._changeState(this.options.lastState);
                this.element.parent().css('display','inline');  
            }
        },
        show:function(){
            if(this.options.state=='min')
                this._minClick();
        },
        _maxClick:function(){
            this.options.maxButPic.toggleClass('ui-icon-newwin').toggleClass('ui-icon-bookmark');           
            this._changeState((this.options.state=='normal')?'max':'normal');
        },
        _changeState:function(state){
        var css=eval('this.options.'+state+'css');
            var that=this;
            var win=this.element.parent();
            if(state=='normal'){
                this.element.dialog('option','draggable',true);
            }else{
                if(this.options.state=='normal'){
                    this.options.normalcss=$.extend(this.options.normalcss,{
                        left:win.css('left'),
                        top:win.css('top'),
                        width:win.css('width'),
                        height:win.css('height'),
                    });
                }
                this.element.dialog('option','draggable',false);
            }
            this.element.parent().animate(css,200,function(){
                if(that.options.state=='min') that.element.parent().css('display','none');
                that._maxAdjust();
            });
            this.options.state=state;
        },
        _maxAdjust:function(){
            if(this.options.state=='max'){
                this.element.parent().width($(window).width()-1).height($(window).height());
            }
        },
        _destroy: function() {
            $.windows.splice(this.options.handler-1,1);
        },
        _setOptions: function() {
            this._superApply( arguments );
        },
        _setOption: function( key, value ) {//alert(value);
            this.element.dialog('option',key,value);
            this._super( key, value );
        }
    });
})(jQuery)