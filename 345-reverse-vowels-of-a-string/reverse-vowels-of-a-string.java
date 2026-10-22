
class Solution {
    public String reverseVowels(String s) {
        char[] ch = s.toCharArray();

        int left = 0;
        int right = ch.length - 1;

        while (left < right) {

            while (left < right && !isVowel(ch[left])) {
                left++;
            }

            while (left < right && !isVowel(ch[right])) {
                right--;
            }

            if (left < right) {
                char temp = ch[left];
                ch[left] = ch[right];
                ch[right] = temp;

                left++;
                right--;
            }
        }

        return new String(ch);
    }

    private boolean isVowel(char c) {
        return "aeiouAEIOU".indexOf(c) != -1;
    }
}