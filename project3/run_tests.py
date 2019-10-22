import datetime, os, signal, subprocess, sys, time, unittest

def run(command, stdin = None, timeout = 30):
    """
    Runs the specified command using specified standard input (if any) and
    returns the output on success. If the command doesn't return within the
    specified time (in seconds), "__TIMEOUT__" is returned.
    """

    start = datetime.datetime.now()
    process = subprocess.Popen(command.split(),
                               stdin = subprocess.PIPE, 
                               stdout = subprocess.PIPE,
                               stderr = subprocess.STDOUT)
    if not stdin is None:
        process.stdin.write(bytes(stdin, 'utf-8'))
    process.stdin.close()
    while process.poll() is None:
        time.sleep(0.1)
        now = datetime.datetime.now()
        if (now - start).seconds > timeout:
            os.kill(process.pid, signal.SIGKILL)
            os.waitpid(-1, os.WNOHANG)
            return "__TIMEOUT__"
    result = process.stdout.read().decode("utf-8")
    process.stdout.close()
    return result

class Exercise1(unittest.TestCase):
    
    def test1(self):
        command = """java Die 5 3 3"""
        sought = """*   *
  *  
*   *
false
true
true
false
"""
        got = run(command)
        self.assertNotEqual(got, "__TIMEOUT__")
        self.assertEqual(got, sought)

class Exercise2(unittest.TestCase):
    
    def test1(self):
        command = "java Location 4 40.6769 117.2319"
        sought = """The Colosseum (Italy) (41.8902, 12.4923)
Petra (Jordan) (30.3286, 35.4419)
Taj Mahal (India) (27.175, 78.0419)
Christ the Redeemer (Brazil) (22.9519, -43.2106)
The Great Wall of China (China) (40.6769, 117.2319)
Chichen Itza (Mexico) (20.6829, -88.5686)
Machu Picchu (Peru) (-13.1633, -72.5456)
true
"""
        got = run(command)
        self.assertNotEqual(got, "__TIMEOUT__")
        self.assertEqual(got, sought)

class Exercise3(unittest.TestCase):
    
    def test1(self):
        command = "java Point3D"
        sought = """(-3.0, 1.0, 6.0)
(0.0, 5.0, 8.0)
(-5.0, -7.0, -3.0)
(-3.0, 1.0, 6.0)
(-5.0, -7.0, -3.0)
(0.0, 5.0, 8.0)
(-5.0, -7.0, -3.0)
(-3.0, 1.0, 6.0)
(0.0, 5.0, 8.0)
(-5.0, -7.0, -3.0)
(-3.0, 1.0, 6.0)
(0.0, 5.0, 8.0)
(-5.0, -7.0, -3.0)
(-3.0, 1.0, 6.0)
(0.0, 5.0, 8.0)
"""
        got = run(command, "3 -3 1 6 0 5 8 -5 -7 -3")
        self.assertNotEqual(got, "__TIMEOUT__")
        self.assertEqual(got, sought)
        
class Problem1(unittest.TestCase):
    
    def test1(self):
        command = "java Term data/cities.txt 5"
        sought = """Top 5 by lexicographic order:
2200	's Gravenmoer, Netherlands
19190	's-Gravenzande, Netherlands
134520	's-Hertogenbosch, Netherlands
3628	't Hofke, Netherlands
246056	A Coruña, Spain
Top 5 by reverse-weight order:
14608512	Shanghai, China
13076300	Buenos Aires, Argentina
12691836	Mumbai, India
12294193	Mexico City, Distrito Federal, Mexico
11624219	Karachi, Pakistan
"""
        got = run(command)
        self.assertNotEqual(got, "__TIMEOUT__")
        self.assertEqual(got, sought)

class Problem2(unittest.TestCase):
    
    def test1(self):
        command = "java BinarySearchDeluxe data/wiktionary.txt cook"
        sought = """3
"""
        got = run(command)
        self.assertNotEqual(got, "__TIMEOUT__")
        self.assertEqual(got, sought)

class Problem3(unittest.TestCase):
    
    def test1(self):
        command = "java Autocomplete data/cities.txt 10"
        sought = """52851	Istaravshan, Tajikistan
44373	Istres, France
40000	Istok, Kosovo
33390	Istra, Russia
13788	Istmina, Colombia
7763	Istrana, Italy
5670	Istok, Russia
4895	Istebna, Poland
4199	Istiaía, Greece
2591	Istria, Romania
"""
        got = run(command, "Ist")
        self.assertNotEqual(got, "__TIMEOUT__")
        self.assertEqual(got, sought)

if __name__ == "__main__":
    unittest.main()
