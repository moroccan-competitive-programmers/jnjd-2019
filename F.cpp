#include <bits/stdc++.h>
using namespace std;

/*Special Case of Josephus problem

##### Special Case when k = 2 #####

There is an elegant way to determine the position of the last surviving person for k = 2 using
binary representation of the number n. If n = 1b 1 b 2 b 3 ..b n then the answer is b 1 b 2 b 3 ..b n 1, i.e.
we move the most significant bit of n to the back to make it the least significant bit. This
way, the Josephus problem with k = 2 can be solved in O(1).

source: Competitive Programming 3
*/

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(0);
	cout.tie(0);

	int tc;
	cin >> tc;
	while (tc--) {
		unsigned long long n ;
		cin >> n;
		bitset<64> myBits(n);

		//Set the left most true bit to Zero
		for (int i = 63 ; i >= 0 ; --i) {
			if ( myBits[i] ) {
				myBits[i] = 0;
				break;
			}
		}

		//Shift to the left by one
		myBits <<= 1;

		// add + 1 (equivalent to operator OR, since after the shift, the last bit value is 0)
		myBits |=  1;
		cout << myBits.to_ullong() << endl;
	}
	return 0;
}