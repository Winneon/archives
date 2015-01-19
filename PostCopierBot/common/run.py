import sys, os, re, time, argparse, getpass, praw, requests, reddit, config

current = {

	'credentials': {
	
		'username': '',
		'password': '',
		
	},
	
}

config_name = 'config.json'

def bootup():
	
	version = '1.0'
	
	parse = argparse.ArgumentParser(description = 'PostCopierBot')
	parse.add_argument('-l', '--login', action = 'store_true', help = 'Login to a different account than config account')
	args = parse.parse_args()
	
	print('\nPCB // version ' + version)
	print('------------------')
	
	if not os.path.isfile(config_name):
		
		config.write(current, config_name)
		print('> Created config.json. Please edit the values in the config before continuing.')
		
		sys.exit()
		
	conf = config.load(config_name)
	
	if args.login:
		
		user = raw_input('> Reddit username: ')
		passwd = getpass.getpass("> %s's password: " % user)
		
		print
		
	else:
		
		user = conf['credentials']['username']
		passwd = conf['credentials']['password']
		
		current['credentials']['username'] = user
		current['credentials']['password'] = passwd
		
	agent = (
		'/u/' + user + ' running PostCopierBot, version ' + version + ', created by /u/WinneonSword.'
	)
	
	r = praw.Reddit(user_agent = agent)
	reddit.login(user, passwd, r)
	
	loop(r)
	
def loop(reddit):
	
	print('\n> Loading the cache...')
	cache = reddit.get_info(thing_id = 't3_20l1np').selftext.split()
	
	try:
		
		while True:
			
			print('\n> Checking for new submissions')
			submissions = reddit.get_subreddit('needadvice').get_new(limit = 10)
			
			for submission in submissions:
				
				if submission.id not in cache:
					
					print('\n> Valid submission found!')
					cache = post(reddit, submission, cache)
					
			time.sleep(15)
			
	except KeyboardInterrupt:
		
		print('\n> Stopping PCB...')
		config.write(current, config_name)
		
		print('> Stopped PCB. Thank you for running this bot!')
		
	'''except:
		
		print('\n> An error has occured. Restarting the bot.')
		loop(reddit)'''
		
def post(reddit, submission, cache):
	
	try:
		
		reddit.submit('NeedAdviceArchive', '[COPY] ' + submission.title, text = (
			'The text in this post is a copy of the original post, which can be found [here](' + submission.permalink + ').\n\n'
			'*****\n' + 
			submission.selftext
		))
		
		print('> Posted the copied post with the title "' + submission.title + '"!')
		cache.append(submission.id)
		
		sub = reddit.get_info(thing_id = 't3_20l1np')
		sub.edit(
			sub.selftext + ' ' + submission.id
		)
		
		print('> Updated the cache successfully!')
		
	except:
		
		print('> Failed to post the copied submission.')
		cache.append(submission.id)
		
	return cache
	
bootup()