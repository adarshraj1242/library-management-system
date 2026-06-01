import sys

try:
    import pypdf
except ImportError:
    import os
    os.system('pip install pypdf')
    import pypdf

def extract_text(pdf_path):
    try:
        reader = pypdf.PdfReader(pdf_path)
        text = ""
        for i, page in enumerate(reader.pages):
            text += f"--- Page {i+1} ---\n"
            text += page.extract_text() + "\n"
        print(text[:4000]) # Print first 4000 chars to avoid overload
        if len(text) > 4000:
            print(f"\n... (truncated, total length {len(text)} chars)")
    except Exception as e:
        print(f"Error: {e}")

if __name__ == "__main__":
    if len(sys.argv) > 1:
        extract_text(sys.argv[1])
    else:
        print("Provide PDF path")
