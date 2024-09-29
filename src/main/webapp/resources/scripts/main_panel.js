function clicked () {
      checked = document.getElementById('main_side_panel_toggle').checked;

      bars = document.getElementsByClassName('main_side_panel_bars')
      cross_1 = document.getElementById('main_side_panel_cross_1')
      cross_2 = document.getElementById('main_side_panel_cross_2')
      icon = document.getElementById('main_side_panel_icon')
      menuItems = document.getElementsByClassName('main_side_panel_menuItem')
      menuBr = document.getElementsByClassName('main_side_panel_br')

      if (checked) {
            icon.style.display = 'flex'
            Array.prototype.forEach.call(menuBr, function(element) {
                  element.style.display = 'flex'
            });
            Array.prototype.forEach.call(menuItems, function(element) {
                  element.style.display = 'flex'
            });
            Array.prototype.forEach.call(bars, function(element) {
                  element.className = element.className.replace("main_side_panel_show","main_side_panel_collapse")
            });
            cross_1.className = cross_1.className.replace("main_side_panel_collapse_cross_1","main_side_panel_show_cross_1")
            cross_2.className = cross_2.className.replace("main_side_panel_collapse_cross_2","main_side_panel_show_cross_2")
      } else {
            cross_1.className = cross_1.className.replace("main_side_panel_show_cross_1","main_side_panel_collapse_cross_1")
            cross_2.className = cross_2.className.replace("main_side_panel_show_cross_2","main_side_panel_collapse_cross_2")
            Array.prototype.forEach.call(bars, function(element) {
                  element.className = element.className.replace("main_side_panel_collapse","main_side_panel_show")
            });
            Array.prototype.forEach.call(menuItems, function(element) {
                  element.style.display = 'none'
            });
            Array.prototype.forEach.call(menuBr, function(element) {
                  element.style.display = 'none'
            });
            icon.style.display = 'none'
      }
}

/*
slides[i].style.display = 'flex'
                    dots[i].className += " active";
               } else {
                    slides[i].style.display = 'none'
                    dots[i].className = dots[i].className.replace(" active", "");
*/