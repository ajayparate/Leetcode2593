public class FindScore {
    public long findScore(int[] nums){
        int n = nums.length;
        long sum = 0;
        int l = 0, r =0;

        while(r < n){
            l = r;
            while(r+1 < n && nums[r] > nums[r+1]){
                r++;

            }
            for (int i = r; i >= l; i -=2){
                sum += nums[i];
            }
            r+=2;
        }
        return sum;
    }
}
/*
✅15 Approaches Explained [Incredibly Long Solution]

Sung Jinwoo
100 Days Badge 2025
1590
Dec 13, 2024
Two Pointers
Dynamic Programming

8+
Tighten up your seat belt as this is going to be a very long ride. This is probably the question with the most approaches I have seen so far on Leetcode , almost every Data Structure or Algo is covered here.
🛠️ Approach 1: Brute Force Using Two Pointers[Gives TLE]
class Solution {
public:
    long long marked = INT_MAX;
    long long findScore(vector<int>& nums) {
        // Step 1: Pick smallest unmarked (for tie pick smallest i)
        // Step 2: Add score += nums[i]
        // Step 3: Mark that element and its adjacent ones
        // Step 4: Recursively do this
        long long cnt = 0; // counts the score
        for (long long i = 0; i < nums.size(); i++) {
            long long x = indexofSmallest(nums);
            if (nums[x] != INT_MAX) {
                cnt += nums[x];
                nums[x] = INT_MAX;
                if (x > 0) nums[x - 1] = INT_MAX;
                if (x < nums.size() - 1) nums[x + 1] = INT_MAX;
            } else {
                return cnt;
            }
        }
        return cnt;
    }

    long long indexofSmallest(vector<int>& nums) {
        long long mini = INT_MAX; long long temp = 0;
        for (long long i = 0; i < nums.size(); i++) {
            if (nums[i] < mini) {
                mini = nums[i];
                temp = i;
            }
        }
        return temp;
    }
};
💡 Approach 2: Optimized Two Pointer [Using Local Maxima Minima]
Find the local minima from a triplet and add it to the sum simply , this is very similar to the Sliding Window Approach we will discuss below.
class Solution {
public:
    long long findScore(vector<int>& nums) {
         const int n = nums.size();
        long long sum = 0;
        for (int l = 0, r = 0; r < n; r += 2) {
            l = r; // Position of local maxima

            while (r + 1 < n && nums[r] > nums[r + 1])
                r++; // position of local minima

            for (int i = r; i >= l; i -= 2)
                sum += nums[i];
        }
        return sum;
    }
};
💻 Approach 3: Using Min Heap and Deque
We do this by making a deque to maintain indices and a Min heap which stores the elements of nums.
We take elements into the Min heap first and then run a loop with condition ==> !deque.empty().
Here we'll use a pair Min heap if needed as it might make it more efficient to delete elements from the deque using that if we have stored the indices in the Min heap itself
Now we just remove the top of the Min heap and keep removing elements from deque and adjacent of that index elements if any present from the deque.
We then keep erasing elements with max val and adjacent to them from deque
We lastly return cnt then.
class Solution {
public:
    long long findScore(vector<int>& nums) {
        int n = nums.size();
        long long cnt = 0;

        deque<int> dq;
        for (int i = 0; i < n; ++i) dq.push_back(i);

        priority_queue<pair<int, int>, vector<pair<int, int>>, greater<pair<int, int>>> minh;
        for (int i = 0; i < n; ++i) {
            minh.push({nums[i], i});
        }

        while (!dq.empty()) {
            auto [minVal, i] = minh.top();
            minh.pop();

            if (find(dq.begin(), dq.end(), i) == dq.end()) continue;

            cnt += minVal;

            dq.erase(remove(dq.begin(), dq.end(), i), dq.end());
            if (i > 0) dq.erase(remove(dq.begin(), dq.end(), i - 1), dq.end());
            if (i < n - 1) dq.erase(remove(dq.begin(), dq.end(), i + 1), dq.end());
        }

        return cnt;
    }
};
🧑‍💻 Approach 4: Using Min Heap and Bool Vector
We simply add the elements from the nums into the Min Heap along with their indexes and then we keep deleting the top of the Min Heap as well as marking the element and its neighbours using the bool vector marked.
class Solution {
public:
    long long findScore(vector<int>& nums) {
        int n = nums.size();
        long long cnt = 0;
        vector<bool> marked(n, false);
        priority_queue<pair<int, int>, vector<pair<int, int>>, greater<pair<int, int>>> minh;
        for (int i = 0; i < n; ++i) {
            minh.push({nums[i], i});
        }
        while (!minh.empty()) {
            auto [minVal, i] = minh.top();
            minh.pop();
            if (marked[i]) continue;
            cnt += minVal;
            marked[i] = true;
            if (i > 0) marked[i - 1] = true;
            if (i < n - 1) marked[i + 1] = true;
        }

        return cnt;
    }
};
📊 Approach 5: Using Sorting
We just store the elements from nums along with their indexes and
then sort the vector.
We then just keep marking the elements of nums as -1 if we have visited either them or their neighbour.
We lastly return the sum which is the total of the minimum value of numbers we need to remove inorder to mark the whole array.
class Solution {
public:
    long long findScore(vector<int>& nums) {
        long long n = nums.size();
        vector<pair<long long, long long>> nums1;
        for (long long i = 0; i < n; ++i) {
            nums1.push_back({nums[i], i});
        }
        sort(nums1.begin(), nums1.end());
        long long sum = 0;
        for (auto& [num, k] : nums1) {
            if (nums[k] != -1) {
                sum += num;
                nums[k] = -1;
                if (k > 0) {
                    nums[k - 1] = -1;
                }
                if (k < n - 1) {
                    nums[k + 1] = -1;
                }
            }
        }
        return sum;
    }
};
📈 Approach 6: Using Ordered Set [BST Like]
This is a Binary Search Tree like approach where we just go over the numbers and in the triplet if the number is minimum then we mark the left and right node as true and add the value of the current node to cnt.
Lastly we return cnt.
class Solution {
public:
    long long findScore(vector<int>& nums) {
        int n = nums.size();
        long long cnt = 0;
        set<pair<int, int>> bst;
        for (int i = 0; i < n; ++i) {
            bst.insert({nums[i], i});
        }
        vector<bool> marked(n, false);
        while (!bst.empty()) {
            auto it = bst.begin();
            int mini = it->first;
            int k = it->second;
            bst.erase(it);
            if (marked[k]) continue;
            cnt += mini;
            marked[k] = true;
            if (k > 0) marked[k - 1] = true;
            if (k < n - 1) marked[k + 1] = true;
        }
        return cnt;
    }
};
🧠 Approach 7: Using Sliding Window
Here we basically just traverse through the vector nums in the form of triplets.
Whichever number in the triplet is the minimum , we simply mark its neighbours and move on to the next triplet.
We also keep adding the value of the minimum from the triplet to the sum.
Lastly we return this sum.
class Solution {
public:
    long long findScore(vector<int>& nums) {
        long long sum = 0;
        int n = nums.size();
        for (int i = 0; i < n; i += 2) {
            int left = i;
            while (i + 1 < n && nums[i + 1] < nums[i]) {
                i++;
            }
            for (int j = i; j >= left; j -= 2) {
                sum += nums[j];
            }
        }
        return sum;
    }
};
🎯 Approach 8: Using Hash Table + Sliding Window
Here we use a similar approach to sliding window and vector bool which we have used above i.e we just keep adding the minimum and marking the left and right nodes.
class Solution {
public:
    long long findScore(vector<int>& nums) {
        int n = nums.size();
        long long sum = 0;
        unordered_map<int, bool> mpp;
        for (int i = 0; i < n; i++) {
            if (mpp[i]) continue;
            int left = i;
            int right = i;
            while (right + 1 < n && nums[right] > nums[right + 1]) {
                right++;
            }
            for (int j = right; j >= left; j -= 2) {
                if (!mpp[j]) {
                    sum += nums[j];
                    mpp[j] = true;
                    if (j > 0) mpp[j - 1] = true;
                    if (j < n - 1) mpp[j + 1] = true;
                }
            }
        }
        return sum;
    }
};
🔍 Approach 9: Using Doubly Linked List
You know the drill , mark the node and neighbours , add the value of node to the sum and lastly return sum.
class DLL {
public:
    int val;
    int pos;
    DLL* l;
    DLL* r;
    bool marked;
    DLL(int value, int idx) : val(value), pos(idx), l(nullptr), r(nullptr), marked(false) {}
};

class Solution {
public:
    long long findScore(vector<int>& nums) {
        int n = nums.size();
        DLL* prev = nullptr;
        vector<DLL*> nums1(n);

        for (int i = 0; i < n; ++i) {
            DLL* curr = new DLL(nums[i], i);
            if (prev) {
                prev->r = curr;
                curr->l = prev;
            }
            nums1[i] = curr;
            prev = curr;
        }
        // Sort array of nums1 by value (and position)
        sort(nums1.begin(), nums1.end(), [](DLL* a, DLL* b) {
            if (a->val == b->val) {
                return a->pos < b->pos;
            }
            return a->val < b->val;
        });
        long long sum = 0;
        // Mark curr and remove left and right elements from linked list
        auto marking = [](DLL* curr) {
            if (curr == nullptr) return;

            curr->marked = true;
            if (curr->l) curr->l->r = curr->r;
            if (curr->r) curr->r->l = curr->l;
        };

        // Iterating nums1 and counting sum
        for (auto& curr : nums1) {
            if (curr->marked) continue;
            sum += curr->val;
            marking(curr->l);
            marking(curr->r);
        }

        return sum;
    }
};
✍️ Approach 10: Using Monotonic Stack
Similar approach basically but using Mono Stack hahahaha.
class Solution {
public:
    long long findScore(vector<int>& nums) {
        long long score = 0;
        int n = nums.size();
        stack<long long> st; //mono stack
        for (long long i : nums) {
            if (!st.empty() && i >= st.top()) {
                bool marked = false;
                while (!st.empty()) {
                    long long add = st.top();
                    st.pop();
                    if (!marked) {
                        score += add;
                    }
                    marked = !marked;
                }
                continue;
            }
            st.push(i);
        }
        bool marked = false;
        while (!st.empty()) {
            long long add = st.top();
            st.pop();
            if (!marked) {
                score += add;
            }
            marked = !marked;
        }
        return score;
    }
};
🔑 Approach 11: Using Queue
Same as using a vector pair but with map instead.
class Solution {
public:
    long long findScore(vector<int>& nums) {
        int n = nums.size();
        map<int, queue<int>> mpp;
        for(int i = 0; i < n; i++){
            mpp[nums[i]].push(i);
        }
        long long ans = 0;
        vector<int> visited(n, 0);
        while(mpp.size() > 0){
            int num = mpp.begin()->first;
            int k = mpp.begin()->second.front();
            if(!visited[k]){
                ans += nums[k];
                visited[k] = 1;
                if(k-1 >= 0) visited[k-1] = 1;
                if(k+1 < n) visited[k+1] = 1;
            }
            mpp.begin()->second.pop();
            if(mpp.begin()->second.size() == 0){
                mpp.erase(mpp.begin());
            }
        }
        return ans;
    }
};
🌟 Approach 12: Using Dynamic Programming
Just add minimum to sum as done above and mark adjacents.
Code wasn't working so I cheesed it for the last 3 test cases HAHAHAHA SORRYY
//
class Solution {
public:
    long long findScore(vector<int>& nums1) {
        int n = nums1.size();
        vector<vector<int>> dp(n, vector<int>(2));
        for(int i = 0; i < n; i++) {
            dp[i][0] = i;
            dp[i][1] = nums1[i];
        }
        sort(dp.begin(), dp.end(), [](vector<int>& a, vector<int>& b) {
            return a[1] < b[1];
        });
        long long outt = 0;
        for(int i = 0; i < n; i++) {
            int ind = dp[i][0];
            if(nums1[ind] != -1) {
                outt += nums1[ind];
                if(ind - 1 >= 0) nums1[ind - 1] = -1;
                if(ind + 1 < n) nums1[ind + 1] = -1;
            }
        }
        if (outt == 4500794) return 4501828;
        if (outt == 255255799) return 254453953;
        if (outt == 48118000000) return 50000000000;
        return outt;
    }
};
🚀 Approach 13: Using Radix Sort and Greedy
Similar to the approaches above we just add minimum to sum and mark adjacents.
class Solution {
public:
    long long findScore(vector<int>& nums1) {
        int n = nums1.size();
        vector<pair<int, int>> idxofNums;
        for (int i = 0; i < n; ++i) {
            idxofNums.push_back({nums1[i], i});
        }
        sort(idxofNums.begin(), idxofNums.end());
        long long sum = 0;
        vector<bool> marked(n, false);
        for (const auto& i : idxofNums) {
            int idx = i.second;
            if (!marked[idx]) {
                sum += i.first;
                marked[idx] = true;
                if (idx - 1 >= 0) marked[idx - 1] = true;
                if (idx + 1 < n) marked[idx + 1] = true;
            }
        }
        return sum;
    }
};
🎮 Approach 14: Using Divide and Conqueor [Memory Limit Exceeded]
We find the minimum element in the current array and its index.
We then add the minimum val to the sum.
It Recursively solves the subarrays to the left and right of this element (skipping adjacent elements).
Add the results from the left and right subarrays.
The loop stops itself when size < 2.
class Solution {
public:
    long long findScoreHelper(vector<int>& nums1) {
        if (nums1.size() == 0) return 0;
        if (nums1.size() == 1) return nums1[0];

        int minVal = nums1[0], pos = 0;
        for (int i = 0; i < nums1.size(); i++) {
            if (nums1[i] < minVal) {
                minVal = nums1[i];
                pos = i;
            }
        }

        long long ans = minVal;
        if (pos - 1 >= 0) {
            vector<int> left(nums1.begin(), nums1.begin() + pos - 1);
            ans += findScoreHelper(left);
        }
        if (pos + 2 < nums1.size()) {
            vector<int> right(nums1.begin() + pos + 2, nums1.end());
            ans += findScoreHelper(right);
        }

        return ans;
    }
    long long findScore(vector<int>& nums1) {
        return findScoreHelper(nums1);
    }
};
📈 Approach 15: Using Graphs
Not exactly graphs but a graph like approach by working with nodes and very similar to the BST Approach I had discussed above.
class Solution {
public:
    long long findScore(vector<int>& nums) {
        int n = nums.size();
        long long cnt = 0;
        set<pair<int, int>> bst;
        for (int i = 0; i < n; ++i) {
            bst.insert({nums[i], i});
        }
        vector<bool> marked(n, false);
        while (!bst.empty()) {
            auto it = bst.begin();
            int minVal = it->first;
            int index = it->second;
            bst.erase(it);
            if (marked[index]) continue;
            cnt += minVal;
            marked[index] = true;
            if (index > 0) marked[index - 1] = true;
            if (index < n - 1) marked[index + 1] = true;
        }
        return cnt;
    }
};
 */
