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
        command = "java GreatCircle 48.87 -2.33 37.8 -122.4"
        sought = """8701.389543238289
"""
        got = run(command)
        self.assertNotEqual(got, "__TIMEOUT__")
        self.assertEqual(got, sought)

class Exercise2(unittest.TestCase):

    def test1(self):
        command = """java PrimeCounter 100"""
        sought = """25
"""
        got = run(command)
        self.assertNotEqual(got, "__TIMEOUT__")
        self.assertEqual(got, sought)

    def test2(self):
        command = """java PrimeCounter 1000000"""
        sought = """78498
"""
        got = run(command)
        self.assertNotEqual(got, "__TIMEOUT__")
        self.assertEqual(got, sought)

class Exercise3(unittest.TestCase):

    def test1(self):
        command = """java Distance"""
        sought = """13.0
"""
        got = run(command, "5 -9 1 10 -1 1 5 -5 9 6 7 4")
        self.assertNotEqual(got, "__TIMEOUT__")
        self.assertEqual(got, sought)
        
class Exercise4(unittest.TestCase):

    def test1(self):
        command = """java Transpose"""
        sought = """3 3
  1.00000   4.00000   7.00000 
  2.00000   5.00000   8.00000 
  3.00000   6.00000   9.00000 
"""
        got = run(command, "3 3 1 2 3 4 5 6 7 8 9")
        self.assertNotEqual(got, "__TIMEOUT__")
        self.assertEqual(got, sought)

class Exercise5(unittest.TestCase):
    
    def test1(self):
        command = "java Rational 10"
        sought = """true
"""
        got = run(command)
        self.assertNotEqual(got, "__TIMEOUT__")
        self.assertEqual(got, sought)

class Exercise6(unittest.TestCase):
    
    def test1(self):
        command = "java Harmonic 10"
        sought = """7381/2520
"""
        got = run(command)
        self.assertNotEqual(got, "__TIMEOUT__")
        self.assertEqual(got, sought)
        
class Problem1(unittest.TestCase):
    
    def test1(self):
        command = "java Percolation data/input10.txt"
        sought = """56 open sites
percolates
"""
        got = run(command)
        self.assertNotEqual(got, "__TIMEOUT__")
        self.assertEqual(got, sought)

    def test2(self):
        command = "java Percolation data/input10-no.txt"
        sought = """55 open sites
does not percolate
"""
        got = run(command)
        self.assertNotEqual(got, "__TIMEOUT__")
        self.assertEqual(got, sought)

class Problem2(unittest.TestCase):
    
    def test1(self):
        command = "java PercolationStats 50 10000"
        got = run(command)
        self.assertNotEqual(got, "__TIMEOUT__")
        toks = got.split()
        mu, sigma, clow, chigh = toks[2], float(toks[5]), toks[8], toks[11]
        self.assertTrue(mu.startswith("0.59") and sigma < 0.1 and clow.startswith("0.59") and chigh.startswith("0.59"))

if __name__ == "__main__":
    unittest.main()
