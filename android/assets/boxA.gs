[agenda]
    change_state do_nothing
[/agenda]
[state "do_nothing"]
	[trigger interaction boxA.button_press]
	    condition_start world.electricity == true
	        operation world.light = on
	    condition_end
	[/trigger]
[/state]