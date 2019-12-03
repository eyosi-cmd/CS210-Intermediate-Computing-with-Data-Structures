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

def sort(s):
    """
    Returns sorted copy of string s.
    """

    return ''.join(sorted(s))

class Exercise1(unittest.TestCase):
    
    def test1(self):
        command = "java ArrayST Pluto"
        sought = """Mercury 1
Venus 2
Earth 3
Mars 4
Jupiter 5
Saturn 6
Uranus 7
Neptune 8
"""
        got = run(command, "Mercury Venus Earth Mars Jupiter Saturn Uranus Neptune Pluto")
        self.assertNotEqual(got, "__TIMEOUT__")
        self.assertEqual(sort(got), sort(sought))

class Exercise2(unittest.TestCase):
    
    def test1(self):
        command = "java Spell data/misspellings.txt"
        sought = """wont:5370 -> won't
unconciousness:16122 -> unconsciousness
accidently:18948 -> accidentally
leaded:21907 -> led
wont:22062 -> won't
aquaintance:30601 -> acquaintance
wont:39087 -> won't
wont:50591 -> won't
planed:53591 -> planned
wont:53960 -> won't
Ukranian:58064 -> Ukrainian
wont:59650 -> won't
conciousness:59835 -> consciousness
occuring:59928 -> occurring
"""
        fh = open("data/war_and_peace.txt", "r")
        got = run(command, fh.read())
        fh.close()
        self.assertNotEqual(got, "__TIMEOUT__")
        self.assertEqual(got, sought)

class Problem1(unittest.TestCase):
    
    def test1(self):
        command = "java BrutePointST 0.661633 0.287141 0.65 0.68 0.28 0.29 5"
        sought = """st.empty()? false
st.size() = 10000
First 5 values:
  3380
  1585
  8903
  4168
  5971
  7265
st.contains((0.661633, 0.287141))? true
st.range([0.65, 0.68] x [0.28, 0.29]):
  (0.663908, 0.285337)
  (0.661633, 0.287141)
  (0.671793, 0.288608)
st.nearest((0.661633, 0.287141)) = (0.663908, 0.285337)
st.nearest((0.661633, 0.287141), 5):
  (0.663908, 0.285337)
  (0.658329, 0.290039)
  (0.671793, 0.288608)
  (0.65471, 0.276885)
  (0.668229, 0.276482)
"""
        fh = open("data/input10K.txt", "r")
        got = run(command, fh.read())
        fh.close()
        self.assertNotEqual(got, "__TIMEOUT__")
        self.assertEqual(got, sought)

class Problem2(unittest.TestCase):
    
    def test1(self):
        command = "java KdTreePointST 0.661633 0.287141 0.65 0.68 0.28 0.29 5"
        sought = """st.empty()? false
st.size() = 10000
First 5 values:
  0
  2
  1
  4
  3
  62
st.contains((0.661633, 0.287141))? true
st.range([0.65, 0.68] x [0.28, 0.29]):
  (0.671793, 0.288608)
  (0.663908, 0.285337)
  (0.661633, 0.287141)
st.nearest((0.661633, 0.287141)) = (0.663908, 0.285337)
st.nearest((0.661633, 0.287141), 5):
  (0.668229, 0.276482)
  (0.65471, 0.276885)
  (0.671793, 0.288608)
  (0.658329, 0.290039)
  (0.663908, 0.285337)
"""
        fh = open("data/input10K.txt", "r")
        got = run(command, fh.read())
        fh.close()
        self.assertNotEqual(got, "__TIMEOUT__")
        self.assertEqual(got, sought)

if __name__ == "__main__":
    unittest.main()
