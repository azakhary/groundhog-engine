[agenda]
    operation world.electricity = true
    change_state do_nothing
[/agenda]
[state "do_nothing"]
	[trigger interaction wireBox.button_press]
	    condition_start world.electricity == false
	        operation world.electricity = true
	        say "electricity is ON"
	        change_state do_nothing
	    condition_end
	    condition_start world.electricity == true
	        operation world.electricity = false
	        say "electricity is OFF"
	        operation world.light = off
	        change_state do_nothing
	    condition_end
	[/trigger]
[/state]