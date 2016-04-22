[agenda]
    change_state do_nothing
[/agenda]
[state "do_nothing"]
	[trigger interaction boxB.button_press]
	    operation world.light = off
	    say "OFF"
	[/trigger]
[/state]