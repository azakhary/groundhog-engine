[agenda]
    change_state do_nothing
[/agenda]
[state "do_nothing"]
	[trigger value world.light]
	    condition_start world.light == on
	        delay 1
	        say ".. [burp]"
            operation stats.irritation += 20
            condition_start stats.irritation > 50
                say "srsly?... [burp]"
            condition_end
		condition_end
		condition_start world.light == off
            operation stats.irritation -= 10
            say "Thanks... [burp]"
        condition_end
	[/trigger]
[/state]