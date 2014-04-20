'''
Created on 2014-4-8

@author: Wendy Wu
@date: ~~
'''
import sys
import telnetlib


class Config(object):
    '''
    classdocs
    '''
    def _enable_router(self, host, password):
        tn = telnetlib.Telnet(host)
        tn.read_until('Password: ')
        tn.write(password+'\n')
        tn.read_until('>')
        tn.write('en' + '\n')
        tn.read_until('Password: ')
        tn.write(password+'\n')
        return tn
        
    def config_router(self, host, password, commands):
        runlog = ''        
        tn = self._enable_router(host, password)
        for command in commands:   
            data = tn.read_until('#')
            runlog += data        
            tn.write(command.encode('ascii')+"\r\n".encode('ascii'))
        runlog += tn.read_until('#')                                  
        tn.close()
        return runlog
    
    def test_router(self, host, password, commands):
        test_result = self.config_router(host, password, commands)
        return test_result
#test
#conf = Config()
#commands_r1 = ["conf t","int s1/1","ip add 20.0.0.1 255.0.0.0","no sh","router rip","net 20.0.0.0","exit","exit"]
#commands_r2 = ["conf t","int s1/0","ip add 30.0.0.2 255.0.0.0","no sh","int s1/2","ip add 31.0.0.2 255.0.0.0","no sh","router rip","net 31.0.0.0","net 30.0.0.0","exit","exit"]
#commands_r3 = ["conf t","int s1/0","ip add 31.0.0.1 255.0.0.0","no sh","router rip","net 31.0.0.0","exit","exit"]

#print conf.config_router("12.0.0.2", "1234", commands_r1)
#conf.config_router("12.0.0.3", "1234", commands_r2)
#conf.config_router("12.0.0.4", "1234", commands_r3)
        
        
        