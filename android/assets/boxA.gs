[agenda]
    change_state do_nothing
[/agenda]
[state "do_nothing"]
	[trigger interaction boxA.button_press]
	    say "LIGHTS ON"
	    operation world.light = on
	[/trigger]
[/state]